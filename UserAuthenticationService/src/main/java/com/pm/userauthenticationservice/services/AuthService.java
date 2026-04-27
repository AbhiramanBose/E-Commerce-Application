package com.pm.userauthenticationservice.services;

import com.pm.userauthenticationservice.exceptions.PasswordMismatchException;
import com.pm.userauthenticationservice.exceptions.UserAlreadyExistException;
import com.pm.userauthenticationservice.exceptions.UserNotRegisteredException;
import com.pm.userauthenticationservice.models.Role;
import com.pm.userauthenticationservice.models.Session;
import com.pm.userauthenticationservice.models.Status;
import com.pm.userauthenticationservice.models.User;
import com.pm.userauthenticationservice.repos.SessionRepo;
import com.pm.userauthenticationservice.repos.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class AuthService implements IAuthService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; //Need to supply bean

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private SecretKey secretKey;

    @Override
    public User signup(String email, String password) throws UserAlreadyExistException {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if(userOptional.isPresent()){
            throw new UserAlreadyExistException("User already exist");
        }
        User user =new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setCreatedAt(new Date());
        user.setLastUpdatedAt(new Date());
        Role role = new Role();
        role.setValue("CUSTOMER");
        List<Role>roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        userRepo.save(user);
        return user;

    }

    @Override
    public Pair<User,String> login(String email, String password) throws UserNotRegisteredException,PasswordMismatchException{
        Optional<User> userOptional = userRepo.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UserNotRegisteredException("User not registered");
        }
        String storedPassword = userOptional.get().getPassword();
        if(!bCryptPasswordEncoder.matches(password,storedPassword)){
            throw new PasswordMismatchException("Please add correct Password");
        }

//        if(!password.equals(storedPassword)){
//            throw new PasswordMismatchException("Please add correct Password");
//        }

        //Generating JWT
//                String message = "{\n" +
//                "   \"email\": \"anurag@gmail.com\",\n" +
//                "   \"roles\": [\n" +
//                "      \"instructor\",\n" +
//                "      \"buddy\"\n" +
//                "   ],\n" +
//                "   \"expirationDate\": \"2ndApril2025\"\n" +
//                "}";
//        byte[] content = message.getBytes(StandardCharsets.UTF_8);
        //String token = Jwts.builder().content(content).compact();
        //return userOptional.get();

        Map<String,Object> payload=new HashMap<>();
        Long nowInMillis =System.currentTimeMillis();
        payload.put("iat",nowInMillis);
        payload.put("exp",nowInMillis+1000*60*60*24);
        payload.put("userId", userOptional.get().getId());
        payload.put("iss","scaler");
        payload.put("scope",userOptional.get().getRoles());
//        MacAlgorithm algorithm = Jwts.SIG.HS256;
//        SecretKey secretKey = algorithm.key().build();
        String token =Jwts.builder().claims(payload).signWith(secretKey).compact();


        Session session = new Session();
        session.setToken(token);
        session.setUser(userOptional.get());
        session.setStatus(Status.ACTIVE);
        sessionRepo.save(session);

        return new Pair<User,String>(userOptional.get(),token);

        //1.check if a token stored in db is matching this or not.
        //2.check whether token has expired or not. currentTimeStamp < ExpiryTimeStamp
        //In order to get expiry timeStamp, we need to pass token and get payload(Claims) -> get Expiry
    }

    public Boolean validateToken(String token, Long userId){
        Optional<Session> optionalSession =sessionRepo.findByTokenAndUser_Id(token,userId);
        if(optionalSession.isEmpty()){
            return false;
        }
        String persistedToken = optionalSession.get().getToken();
        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();
        Long tokenExpiry =(Long) claims.get("exp");
        Long currentTime = System.currentTimeMillis();

        System.out.println(tokenExpiry);
        System.out.println(currentTime);
        if(currentTime>tokenExpiry){
            Session session = optionalSession.get();
            session.setStatus(Status.INACTIVE);
            sessionRepo.save(session);
            return false;
        }
        return true;
    }
}
