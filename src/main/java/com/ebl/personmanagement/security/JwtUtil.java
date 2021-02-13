package com.ebl.personmanagement.security;

import static com.ebl.personmanagement.security.SecurityConstants.EXPIRATION_TIME;
import static com.ebl.personmanagement.security.SecurityConstants.KEY;

import java.util.Date;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ebl.personmanagement.security.dto.JwtToken;

public abstract class JwtUtil {
	public AuthenticationManager authenticationManager;

	public JwtUtil() {
		throw new IllegalAccessError("Utill class cannot be initiaised");
	}
	
	public static JwtToken generateToken(String username) {
		String token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(KEY.getBytes()));

		return new JwtToken(token, UUID.randomUUID().toString());
	}
	

	public static String verifyToken(String token) {
		// parse the token.
		return JWT.require(Algorithm.HMAC512(KEY.getBytes()))
		        .build()
		        .verify(token)
		        .getSubject();
	}
}