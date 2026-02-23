package dzap.config;

import dzap.application.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration Spring Security avec JWT
 * Endpoints publics : newsletter, auth, swagger
 * Endpoints protégés : /api/admin/** (ADMIN uniquement)
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

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
                // Endpoints publics - Auth
                .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()

                // Endpoints publics - Swagger
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

                // Endpoints publics - Newsletter
                .requestMatchers("/api/newsletter/**").permitAll()

                // Endpoints publics - Quiz (sauf admin)
                .requestMatchers("/api/quiz/questions", "/api/quiz/submit", "/api/quiz/result/**", "/api/quiz/health").permitAll()

                // Endpoints Admin Quiz - Requiert le rôle ADMIN
                .requestMatchers("/api/quiz/admin/**").hasRole("ADMIN")

                // Endpoints Admin - Requiert le rôle ADMIN
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // Tous les autres endpoints nécessitent une authentification
                .anyRequest().authenticated()
            )

            // Ajouter le provider d'authentification
            .authenticationProvider(authenticationProvider())

            // Ajouter le filtre JWT avant le filtre d'authentification par défaut
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}





