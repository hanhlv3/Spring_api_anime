package com.api_anime.anime.service;


import com.api_anime.anime.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "123";

    public String generateToken(User user) {

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        return JWT.create()
                  .withSubject(String.valueOf(user.getUserId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 50*60*1000))
                .withClaim("role", user.getRole())
                .sign(algorithm);

    }
    public String generateRefreshToken(User user) {

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        return JWT.create()
                .withSubject(String.valueOf(user.getUserId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 50*60*1000))
                .sign(algorithm);
    }
}
