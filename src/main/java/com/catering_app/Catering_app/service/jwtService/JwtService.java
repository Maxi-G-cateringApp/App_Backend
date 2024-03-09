package com.catering_app.Catering_app.service.jwtService;

import com.catering_app.Catering_app.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public interface JwtService {


    public String extractUserName(String token);

    public <T> T extractClaims(String token, Function<Claims, T> resolver);

    public boolean isValid(String token, UserDetails user);

    public String generateToken(User user);
    public boolean isTokenExpired(String token);
}

