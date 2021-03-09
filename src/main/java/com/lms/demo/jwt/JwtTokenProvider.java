package com.lms.demo.jwt;


import com.lms.demo.models.databasemoduls.User;
import com.lms.demo.service.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;

    private final UserService userDetailsService;


    private String secretKey;

    private Key key;

    public JwtTokenProvider(JwtProperties jwtProperties, UserService userDetailsService) {
        this.jwtProperties = jwtProperties;
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }


    public String createToken(User user) {
        String claims = user.getEmail();
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getValidityInMs());
        return Jwts.builder()
                .claim("auth",claims)
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(this.key,SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }



}