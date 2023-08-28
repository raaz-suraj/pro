package com.blogapi11.controller;

import com.blogapi11.entity.Role;
import com.blogapi11.entity.User;
import com.blogapi11.payload.JWTAuthResponse;
import com.blogapi11.payload.UserLoginDto;
import com.blogapi11.payload.UserSignup;
import com.blogapi11.repository.RoleRepository;
import com.blogapi11.repository.UserRepository;
import com.blogapi11.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/authResponse")
public class AuthController {

    @Autowired
private JwtTokenProvider tokenProvider;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")  // http://localhost:8080/authResponse/signup
    public ResponseEntity<String> userRegistration(@RequestBody UserSignup signUp){
        if(userRepo.existsByUsername(signUp.getUsername())){
            return new ResponseEntity<>("username is already Exist", HttpStatus.BAD_REQUEST);
        }
        if(userRepo.existsByEmail(signUp.getEmail())){
            return new ResponseEntity<>("Email is already Exist", HttpStatus.BAD_REQUEST);
        }

        User user= new User();
        user.setName(signUp.getName());
        user.setUsername(signUp.getUsername());
        user.setEmail(signUp.getEmail());
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));

        Role roles=roleRepo.findByName("ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepo.save(user);
        return new ResponseEntity<>("user register successfully",HttpStatus.CREATED);
    }

    @PostMapping("/signin")  // http://localhost:8080/authResponse/signin
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody UserLoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }
}
