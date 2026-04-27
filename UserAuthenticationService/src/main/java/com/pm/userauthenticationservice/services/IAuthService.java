package com.pm.userauthenticationservice.services;

import com.pm.userauthenticationservice.exceptions.PasswordMismatchException;
import com.pm.userauthenticationservice.exceptions.UserAlreadyExistException;
import com.pm.userauthenticationservice.exceptions.UserNotRegisteredException;
import com.pm.userauthenticationservice.models.User;
import org.antlr.v4.runtime.misc.Pair;

public interface IAuthService {
    User signup(String email, String password) throws UserAlreadyExistException;
    Pair<User,String > login(String email, String password) throws UserNotRegisteredException, PasswordMismatchException;
    Boolean validateToken(String token, Long userId);
}
