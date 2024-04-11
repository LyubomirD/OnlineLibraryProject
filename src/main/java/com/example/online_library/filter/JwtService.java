package com.example.online_library.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "5418357f19443f7acc1b361d24cf67eeb1c6b04b1881a0cab6528e6a12714340";

    public String extractUsername(String jwt_token) {
        return extractClaim(jwt_token, Claims::getSubject);
    }

    private Claims extractAllClaims(String jwt_token) {
        return Jwts
                .parser()
                .setSigningKey(getSignedKey())
                .build()
                .parseSignedClaims(jwt_token)
                .getPayload();
    }

    private Key getSignedKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String jwt_token, Function<Claims, T> claimResolver) {
        final Claims claim = extractAllClaims(jwt_token);
        return claimResolver.apply(claim);
    }


    public String generateJwT_Token(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignedKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateJwT_Token(UserDetails userDetails) {
        return generateJwT_Token(new HashMap<>(), userDetails);
    }

    public boolean isJwt_TokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isJwt_TokenExpired(token));
    }

    private boolean isJwt_TokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
