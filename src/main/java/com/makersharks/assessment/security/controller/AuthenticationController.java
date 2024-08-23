package com.makersharks.assessment.security.controller;
import com.makersharks.assessment.exception.exeception.DataAlreadyExistException;
import com.makersharks.assessment.exception.exeception.DataNotAcceptableException;
import com.makersharks.assessment.exception.exeception.DataNotFoundException;
import com.makersharks.assessment.security.dto.AuthenticationRequest;
import com.makersharks.assessment.security.dto.RegistrationRequest;
import com.makersharks.assessment.security.dto.RoleDto;
import com.makersharks.assessment.security.dto.RoleToUserDto;
import com.makersharks.assessment.security.response.AuthenticationResponse;
import com.makersharks.assessment.security.service.main.role.RoleService;
import com.makersharks.assessment.security.service.main.user.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RoleService roleService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request) throws MessagingException, DataAlreadyExistException, DataNotFoundException, DataNotAcceptableException {
        return new ResponseEntity<>(authenticationService.saveUser(request),HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return new ResponseEntity<>(authenticationService.authenticate(request),HttpStatus.OK);
    }

    @GetMapping("/activate-account/{token}")
    public ResponseEntity<String> validateToken(@PathVariable("token")String token) throws DataNotFoundException, MessagingException, DataNotAcceptableException {
        return new ResponseEntity<>(authenticationService.activateAccount(token),HttpStatus.OK);
    }

    @PostMapping("/role/add")
    public ResponseEntity<String> saveRole(@RequestBody RoleDto roleDto) throws DataAlreadyExistException {
        return new ResponseEntity<>(roleService.saveRole(roleDto),HttpStatus.OK);
    }

    @PostMapping("/add/role/to/user")
    public ResponseEntity<String> addRoleToUser(@RequestBody RoleToUserDto roleToUserDto) throws DataNotFoundException, DataAlreadyExistException, DataNotFoundException {
        return new ResponseEntity<>(roleService.addRoleToUser(roleToUserDto),HttpStatus.OK);
    }
}
