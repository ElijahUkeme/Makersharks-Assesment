package com.makersharks.assessment.security.service.main.user;


import com.makersharks.assessment.exception.exeception.DataAlreadyExistException;
import com.makersharks.assessment.exception.exeception.DataNotAcceptableException;
import com.makersharks.assessment.exception.exeception.DataNotFoundException;
import com.makersharks.assessment.security.dto.AuthenticationRequest;
import com.makersharks.assessment.security.dto.RegistrationRequest;
import com.makersharks.assessment.security.response.AuthenticationResponse;
import jakarta.mail.MessagingException;

public interface AuthenticationService {

    public String saveUser(RegistrationRequest request) throws MessagingException, DataNotAcceptableException, DataNotFoundException, DataAlreadyExistException;
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    public String activateAccount(String token) throws DataNotFoundException, DataNotAcceptableException, MessagingException;
}
