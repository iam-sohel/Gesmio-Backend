package com.gesmio.platform.identity.auth;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

    private final JwtEncoder encoder;
    private final long expiryMinutes;

    public JwtService(JwtEncoder encoder, @Value("${app.jwt.expiry-minutes}") long expiryMinutes) {
        this.encoder = encoder;
        this.expiryMinutes = expiryMinutes;
    }

    public Instant expiryFrom(Instant issuedAt) {
        return issuedAt.plus(Duration.ofMinutes(expiryMinutes));
    }

    public String issue(UUID userId, String email, Collection<String> roles, Collection<String> permissions,
                        UUID employeeId, Instant issuedAt) {
        JwtClaimsSet.Builder claims = JwtClaimsSet.builder()
                .issuer("gesmio")
                .issuedAt(issuedAt)
                .expiresAt(expiryFrom(issuedAt))
                .subject(userId.toString())
                .claim("email", email)
                .claim("roles", roles)
                .claim("permissions", permissions);
        if (employeeId != null) {
            claims.claim("employeeId", employeeId.toString());
        }
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return encoder.encode(JwtEncoderParameters.from(header, claims.build())).getTokenValue();
    }
}
