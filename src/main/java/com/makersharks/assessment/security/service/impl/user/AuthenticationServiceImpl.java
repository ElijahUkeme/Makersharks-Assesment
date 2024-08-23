package com.makersharks.assessment.security.service.impl.user;
import com.makersharks.assessment.exception.exeception.DataAlreadyExistException;
import com.makersharks.assessment.exception.exeception.DataNotAcceptableException;
import com.makersharks.assessment.exception.exeception.DataNotFoundException;
import com.makersharks.assessment.security.config.JwtService;
import com.makersharks.assessment.security.dto.AuthenticationRequest;
import com.makersharks.assessment.security.dto.EmailDto;
import com.makersharks.assessment.security.dto.RegistrationRequest;
import com.makersharks.assessment.security.entity.AppUser;
import com.makersharks.assessment.security.entity.Token;
import com.makersharks.assessment.security.repository.AppUserRepository;
import com.makersharks.assessment.security.repository.RoleRepository;
import com.makersharks.assessment.security.repository.TokenRepository;
import com.makersharks.assessment.security.response.AuthenticationResponse;
import com.makersharks.assessment.security.service.main.user.AuthenticationService;
import com.makersharks.assessment.security.utils.FieldValidationUtil;
import com.makersharks.assessment.service.main.email.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RoleRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${app.user.confirmation.email.url}")
    private String activationUrl;


    @Override
    public String saveUser(RegistrationRequest request) throws MessagingException, DataNotAcceptableException, DataNotFoundException, DataAlreadyExistException {
        //Register a user and send the 6-digit activation code to the registered email address
            if (Objects.isNull(repository.findByName("MANAGER"))){
                throw new DataNotFoundException("Role USER not found");
            }
            if (appUserRepository.findByEmail(request.getEmail()).isPresent()){
                throw new DataAlreadyExistException("Email Address already used");
            }
            var userRole = repository.findByName("MANAGER");

            if (FieldValidationUtil.validateRegistrationFields(request)){

                AppUser user = AppUser.builder()
                        .email(request.getEmail())
                        .lastname(request.getLastname())
                        .firstname(request.getFirstname())
                        .createdDate(LocalDateTime.now())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .isEnabled(false)
                        .isLocked(false)
                        .roles(List.of(userRole.get()))
                        .build();
                appUserRepository.save(user);
                sendValidationTokenEmail(user);
                return "User Registration Successful";
            }
            return "";
        }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        //authenticate user and returned a signed jwt if authentication passed
            var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()
            ));
            var claims = new HashMap<String,Object>();
            var user = ((AppUser) auth.getPrincipal());
            claims.put("fullName",user.getEmail());
            var jwtToken = jwtService.generateToken(claims,user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }

    @Override
    public String activateAccount(String token) throws DataNotFoundException, DataNotAcceptableException, MessagingException {
        //This method is to activate the token that was sent to the
        //registered user's email during registration
        //It will first check if the token is valid by performing some validation
        //such as checking if the token exist in the db, check if the 20 minutes
        //expiration time has elapsed as well as checking if the token is for the user
            Token retrievedToken = tokenRepository.findByToken(token);
            if (Objects.isNull(retrievedToken)){
                throw new DataNotFoundException("Token not found");
            }
            if (LocalDateTime.now().isAfter(retrievedToken.getExpiredAt())){
                sendValidationTokenEmail(retrievedToken.getUser());
                throw new DataNotAcceptableException("Your validation token has expired and a new token has been sent to your email");
            }
            Optional<AppUser> user = appUserRepository.findByEmail(retrievedToken.getUser().getEmail());
            if(Objects.isNull(user)){
                throw new DataNotFoundException("User not found");
            }
            //update user's status to enabled and set the time the
             //token was validated as well as saving it back to the db
            user.get().setEnabled(true);
            appUserRepository.save(user.get());
            retrievedToken.setValidatedAt(LocalDateTime.now());
            tokenRepository.save(retrievedToken);
            return "Token Validated Successfully";

    }

    private void sendValidationTokenEmail(AppUser user) throws MessagingException {
        //send the random generated token to the user's email
        String newToken = generateAndSaveActivationToken(user);
        EmailDto emailDto = EmailDto.builder()
                .to(user.getEmail())
                .activationCode(newToken)
                .username(user.fullName())
                .templateName("activate_account")
                .subject("Account Activation")
                .confirmationUrl(activationUrl)
                .build();
        emailService.sendConfirmationEmail(emailDto);


    }
    private String generateAndSaveActivationToken(AppUser user){
        //Generate and save the token to the db
        //It's a 6-digit token which will expires after 20 minutes
        //This token will be sent to the registered email address
        //during the registration because the token will be needed before
        //the assigning a jwt to the user
        String generatedToken = generateActivationCode(6);
        Token token = Token.builder()
                .createdAt(LocalDateTime.now())
                .token(generatedToken)
                .expiredAt(LocalDateTime.now().plusMinutes(20))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    //Generate a random number as otp
    private String generateActivationCode(int length){
        String characters = "1234567890";
        StringBuffer codeBuilder = new StringBuffer();
        SecureRandom secureRandom = new SecureRandom();
        for (int i=0;i<length;i++){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
