package com.krakedev.proyectos.security;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JwtUtil {

	 private static final String SECRET = "mi_clave_secreta";

	    public static String generarToken(String username, String rol) {

	        return JWT.create()
	                .withSubject(username)
	                .withClaim("rol", rol)
	                .withIssuedAt(new Date())
	                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
	                .sign(Algorithm.HMAC256(SECRET));
	    }

	    public static DecodedJWT validarToken(String token) {

	        JWTVerifier verifier = JWT.require(
	                Algorithm.HMAC256(SECRET))
	                .build();

	        return verifier.verify(token);
	    }
	}