package com.dungle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dungle.config.JwtProvider;
import com.dungle.models.User;
import com.dungle.repository.UserRepository;
import com.dungle.request.LoginRequest;
import com.dungle.response.AuthResponse;
import com.dungle.service.CustomerUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @PostMapping("/signup")
    public AuthResponse CreateUser(@RequestBody User userEntity) throws Exception {
        User isExist = userRepository.findByEmail(userEntity.getEmail());
        if(isExist != null){
            throw new Exception("this email already user with another account");
        }
        User user = new User();
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setEmail(userEntity.getEmail());
        user.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        User saveUser = userRepository.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(saveUser.getEmail(), saveUser.getPassword());
        String token = JwtProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse(token, "Register success");
        return res;
    }

    @PostMapping("/signin")
    public AuthResponse Signin(@RequestBody LoginRequest loginRequest) throws Exception{
        Authentication authentication = Authenticate(loginRequest.getEmail(),loginRequest.getPassword());
        String token = JwtProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse(token, "Login success");
        return res;
    }

    private Authentication Authenticate(String email, String password) throws Exception {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);

        if(userDetails==null){
            throw new Exception("invalid username");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new Exception("Password not matched");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
    }
}
