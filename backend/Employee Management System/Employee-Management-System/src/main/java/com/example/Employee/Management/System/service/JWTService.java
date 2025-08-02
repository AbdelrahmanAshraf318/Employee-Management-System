package com.example.Employee.Management.System.service;

import com.example.Employee.Management.System.models.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Component
public class JWTService
{
    @Value("${app.jwtSecret}")
    private String secretKey;

    @Value("${app.jwtExpirationMs}")
    private long jwtExpirationInMs;

    private static final Logger logger = LoggerFactory.getLogger(JWTService.class);

    public String generateToken(Authentication auth)
    {
        logger.info("Generating token");
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationInMs);

        logger.info("User Principal : {}", userPrincipal);

        return Jwts.builder().subject(userPrincipal.getUsername()).
                issuedAt(now).expiration(expiry)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromJwt(String token)
    {
        logger.info("Attempting to get username from JWT token");
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseSignedClaims(token).getPayload()
                .getSubject();
    }

    public boolean validateToken(String token)
    {
        logger.info("Validating token");
        try
        {
            Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }
        catch (JwtException | IllegalArgumentException ex)
        {
            logger.error(ex.getMessage());
        }
        return false;
    }

}
