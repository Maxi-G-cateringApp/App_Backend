package com.catering_app.Catering_app.service.jwtService;

import com.catering_app.Catering_app.model.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {


    public String extractUserName(String token);
    public <T> T extractClaims(String token, Function<Claims, T> resolver);
    public boolean isValid(String token, UserDetails user);
    public String generateToken(User user);
    public boolean isTokenExpired(String token);
    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}

