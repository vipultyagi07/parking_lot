package com.vip.security;

import com.vip.dto.UserRepo;
import com.vip.entity.User;
import com.vip.exception.ParkingLotException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {

    private String secret = "your_secret_key";
    @Autowired
    private UserRepo userRepository;

    public JwtUtil() {
        // Encode the secret key using Base64
        this.secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }
    public Long getUserIdFromHttpServletRequest(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ParkingLotException("Authorization header is missing or invalid", HttpStatus.UNAUTHORIZED.toString());
        }

        String jwtToken = authorizationHeader.substring(7); // Remove "Bearer " from the token
        String userEmail = extractUsername(jwtToken);

        User user = userRepository.findByEmailAndIsActiveTrue(userEmail);
        if(Objects.isNull(user)){
            throw new ParkingLotException("No Active user is present for this user", HttpStatus.UNAUTHORIZED.toString());

        }
        return user.getId();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(Base64.getDecoder().decode(secret)).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, Base64.getDecoder().decode(secret)).compact();
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
