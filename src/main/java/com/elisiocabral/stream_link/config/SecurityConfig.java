package com.elisiocabral.stream_link.config;

import com.elisiocabral.stream_link.repository.UserRepository;
import com.elisiocabral.stream_link.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthService authService;

    public SecurityConfig(AuthService authService) {
        this.authService = authService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(
                auth ->{
                    auth.requestMatchers("/", "/login**", "/error**", "/webjars/**",
                            "/css/**", "/js/**", "/images/**",
                            "/about-us", "/contact-us", "/privacy-policy", "/terms-of-use"
                    ).permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(authService)
                        ))
                .oauth2Login(oauth2Login ->
                oauth2Login
                        .defaultSuccessUrl("/", true)
        )
                .build();
    }
}
