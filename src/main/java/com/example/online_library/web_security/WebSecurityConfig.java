package com.example.online_library.web_security;

import com.example.online_library.filter.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final AuthenticationProviderConfig authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;

    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/api/v*/library-admin/**").permitAll()
                            .requestMatchers("/api/v*/library-user/**").permitAll()
                            .requestMatchers("/api/v*/registration/**").permitAll()
                            .requestMatchers("/api/v*/book-borrow/**").permitAll()
                            .requestMatchers("/api/v*/login/**").permitAll()
                            .requestMatchers("/api/v*/logout/**").permitAll()

                            .requestMatchers(WHITE_LIST_URL).permitAll();
                }
        );

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authenticationProvider(authenticationProvider.authenticationProvider());

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

//        http.logout(logout -> logout.logoutUrl("/api/v*/logout/**").addLogoutHandler(logoutHandler)
//                .logoutSuccessHandler(
//                        (request, response, authentication) -> SecurityContextHolder.clearContext())
//        );

        return http.build();
    }
}

