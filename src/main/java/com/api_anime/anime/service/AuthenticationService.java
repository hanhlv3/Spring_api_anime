package com.api_anime.anime.service;


import com.api_anime.anime.auth.AuthenticationRequest;
import com.api_anime.anime.auth.AuthenticationResponse;
import com.api_anime.anime.entity.User;
import com.api_anime.anime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserEmail(), authenticationRequest.getPassword()));

        User user = userRepository.findByEmail(authenticationRequest.getUserEmail()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);
        String jwtRefreshToken  = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder().token(jwtToken).refreshToken(jwtRefreshToken).build();

    }

}
