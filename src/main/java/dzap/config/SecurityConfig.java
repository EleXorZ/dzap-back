package dzap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration Spring Security
 * Endpoints publics : newsletter, quiz, swagger
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Désactiver CSRF pour les API REST (stateless)
            .csrf(csrf -> csrf.disable())

            // Activer CORS
            .cors(cors -> {})

            // Stateless session
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Configuration des autorisations
            .authorizeHttpRequests(authz -> authz
                // Tous les endpoints - Public
                .anyRequest().permitAll()
            );

        return http.build();
    }
}





