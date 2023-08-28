package com.blogapi11.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenProvider {

    @Value("${app.Jwt-secret}")
    private String secret;

    // generate Token
    public String generateToken(Authentication authentication){
        String name = authentication.getName();
        String token = Jwts.builder().setSubject(name)
                .setIssuer("Raaz")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
        return  token;
    }

    //  Read Claims
    public Claims getClaims(String token){
        Claims body = Jwts.parser().setSigningKey(secret.getBytes())
                .parseClaimsJws(token).getBody();
        return body;
    }

    // public Expire Date
    public Date getExpDate(String token){
        return getClaims(token).getExpiration();
    }

    // Read Subject /username
    public String getUsername(String token){
        return getClaims(token).getSubject();
    }

    // validate ExpDate
    public boolean isTokenExp(String token){
        Date expDate=getExpDate(token);
        return expDate.before(new Date(System.currentTimeMillis()));
    }

    // validate username in token and database and expDate
    public boolean validateToken(String token){
        return (!isTokenExp(token));
    }

}
