package com.blogapi11.util;

import com.blogapi11.entity.User;
import com.blogapi11.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
 private UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
                () -> new UsernameNotFoundException("username or email not found")
        );
      return new  org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword()
      ,user.getRoles().stream().map(roles->new SimpleGrantedAuthority(roles.getName())).collect(Collectors.toList()));

    }
}
