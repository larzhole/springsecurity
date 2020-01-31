package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class JwtProvider {

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Value("${jwt.secret}")
    private String jwtSecret;

    String createToken(Authentication auth, String username) {
        return JWT.create()
                .withIssuer(jwtIssuer)
                .withIssuedAt(Date.from(Instant.now()))
                .withClaim("un", username)
                .withClaim("gr", getGroup(auth.getAuthorities()))
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    void verifyToken(String token) {
        final JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtSecret))
                .withIssuer(jwtIssuer)
                .build();
        jwtVerifier.verify(token);
    }

    UsernamePasswordAuthenticationToken getAuthenticationFromToken(String token) {
        final DecodedJWT decodedJWT = JWT.decode(token);
        final List<SimpleGrantedAuthority> authorityList = getAuthorities(decodedJWT);
        final User user = new User(decodedJWT.getClaim("un").asString(), "pw", authorityList);

        return new UsernamePasswordAuthenticationToken(user, null, authorityList);
    }

    private List<SimpleGrantedAuthority> getAuthorities(DecodedJWT decodedJWT) {
        final Claim claim = decodedJWT.getClaim("gr");
        final List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        final String[] roles = claim.asString()
                .replaceAll("\\[", "")
                .replaceAll("]", "")
                .split(", ");

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }

    private String getGroup(Collection<? extends GrantedAuthority> grantedAuthorities) {
        if (!grantedAuthorities.isEmpty()) {
            final Optional authority = grantedAuthorities.stream()
                    .filter(a -> SecurityRoles.ALL_ROLES.contains(a.getAuthority()))
                    .findFirst();

            if (authority.isPresent()) {
                return authority.get().toString();
            }
        }
        return null;
    }
}
