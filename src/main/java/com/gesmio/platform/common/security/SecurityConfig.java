
package com.gesmio.platform.common.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(s ->
                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(a -> a

                        // Actuator endpoints
                        .requestMatchers(
                                "/actuator/health",
                                "/actuator/info"
                        ).permitAll()

                        // Swagger and OpenAPI documentation
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Public authentication endpoint
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/auth/login"
                        ).permitAll()

                        // Authenticated self-service endpoints
                        .requestMatchers(
                                "/api/v1/me/**",
                                "/api/v1/auth/**"
                        ).authenticated()

                        // Manager endpoints
                        .requestMatchers("/api/v1/manager/**")
                        .hasAnyRole("ADMIN", "HR", "MANAGER")

                        // Admin-only modules
                        .requestMatchers(
                                "/api/v1/identity/**",
                                "/api/v1/authorization/**",
                                "/api/v1/administration/**",
                                "/api/v1/audit/**"
                        ).hasRole("ADMIN")

                        // Recruitment module
                        .requestMatchers("/api/v1/recruitment/**")
                        .hasAnyRole("ADMIN", "HR")

                        // Organization and employee read access
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/organization/**",
                                "/api/v1/employees/**",
                                "/api/v1/announcements/**",
                                "/api/v1/leave/leave-policies/**",
                                "/api/v1/support/support-categories/**"
                        ).authenticated()

                        // Organization and employee write access
                        .requestMatchers(
                                "/api/v1/organization/**",
                                "/api/v1/employees/**",
                                "/api/v1/announcements/**"
                        ).hasAnyRole("ADMIN", "HR")

                        // Products, projects and tasks read access
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/products/**",
                                "/api/v1/projects/**",
                                "/api/v1/tasks/**"
                        ).authenticated()

                        // Products, projects and tasks write access
                        .requestMatchers(
                                "/api/v1/products/**",
                                "/api/v1/projects/**",
                                "/api/v1/tasks/**"
                        ).hasAnyRole("ADMIN", "HR", "MANAGER")

                        // Leave and attendance read access
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/leave/**",
                                "/api/v1/attendance/**"
                        ).hasAnyRole("ADMIN", "HR", "MANAGER")

                        // Leave, attendance, support and documents write access
                        .requestMatchers(
                                "/api/v1/leave/**",
                                "/api/v1/attendance/**",
                                "/api/v1/support/**",
                                "/api/v1/documents/**"
                        ).hasAnyRole("ADMIN", "HR")

                        // All remaining endpoints require authentication
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(o ->
                        o.jwt(j ->
                                j.jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );

        return http.build();
    }

    /**
     * Converts roles from the JWT "roles" claim
     * into Spring Security ROLE_ authorities.
     */
    private JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter authorities =
                new JwtGrantedAuthoritiesConverter();

        authorities.setAuthoritiesClaimName("roles");
        authorities.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(authorities);

        return converter;
    }

    /**
     * BCrypt password encoder.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * JWT secret key using HS256.
     */
    @Bean
    SecretKey jwtSecretKey(
            @Value("${app.jwt.secret}") String secret
    ) {

        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);

        if (bytes.length < 32) {
            throw new IllegalStateException(
                    "JWT_SECRET must be at least 32 characters long"
            );
        }

        return new SecretKeySpec(bytes, "HmacSHA256");
    }

    /**
     * JWT encoder.
     */
    @Bean
    JwtEncoder jwtEncoder(SecretKey jwtSecretKey) {
        return new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey)
        );
    }

    /**
     * JWT decoder using HS256.
     */
    @Bean
    JwtDecoder jwtDecoder(SecretKey jwtSecretKey) {
        return NimbusJwtDecoder
                .withSecretKey(jwtSecretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    /**
     * CORS configuration.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource(
            @Value("${app.cors.allowed-origins}") String origins
    ) {

        CorsConfiguration cfg = new CorsConfiguration();

        cfg.setAllowedOrigins(
                Arrays.stream(origins.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .toList()
        );

        cfg.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        cfg.setAllowedHeaders(
                List.of("Authorization", "Content-Type", "X-Request-Id")
        );

        cfg.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/api/**", cfg);

        return source;
    }
}
