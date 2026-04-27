package com.pm.userauthenticationservice.controllers;

import com.pm.userauthenticationservice.dtos.LoginRequest;
import com.pm.userauthenticationservice.dtos.SignupRequest;
import com.pm.userauthenticationservice.dtos.UserDto;
import com.pm.userauthenticationservice.dtos.ValidateTokenDto;
import com.pm.userauthenticationservice.exceptions.PasswordMismatchException;
import com.pm.userauthenticationservice.exceptions.UnauthorizedException;
import com.pm.userauthenticationservice.exceptions.UserAlreadyExistException;
import com.pm.userauthenticationservice.exceptions.UserNotRegisteredException;
import com.pm.userauthenticationservice.models.User;
import com.pm.userauthenticationservice.services.IAuthService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IAuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequest signupRequest){
        try{
            User user = authService.signup(signupRequest.getEmail(),signupRequest.getPassword());
            //return new ResponseEntity<>(null, HttpStatus.CREATED);
            return new ResponseEntity<>(from(user), HttpStatus.CREATED);

        }catch (UserAlreadyExistException exception){

            //return ResponseEntity.badRequest().build();
            return new ResponseEntity<>((HttpHeaders) null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequest loginRequest){
        try {
            Pair<User,String> response = authService.login(loginRequest.getEmail(),loginRequest.getPassword());
            MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
            headers.add(HttpHeaders.SET_COOKIE,response.b);
            return new ResponseEntity<>(from(response.a), headers,HttpStatus.OK);
        } catch (UserNotRegisteredException exception) {
            return ResponseEntity.notFound().build();
        } catch (PasswordMismatchException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/validateToken")
    public Boolean validateToken(@RequestBody ValidateTokenDto validateTokenDto) throws UnauthorizedException {
        Boolean result = authService.validateToken(validateTokenDto.getToken(), validateTokenDto.getUserId());
        if(!result){
            throw new UnauthorizedException("Please login again, Inconvenience regretted");
        }
        return result;
    }

    public UserDto from(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
