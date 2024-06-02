package com.TournamentTracker.config;

import com.TournamentTracker.security.jwt.JwtRequestFilter;
import com.TournamentTracker.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Qualifier("customAccessDeniedHandler")
    private final AccessDeniedHandler accessDeniedHandler;
    private static final String[] SWAGGER_PATHS = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-resources/**",
            "/v3/**"
    };
    private static final String USER_CONTROLLER_PATH = "/api/users/**";
    private static final String SPORT_CONTROLLER_PATH = "/api/sports/**";
    private static final String RULE_CONTROLLER_PATH = "/api/rules/**";
    private static final String[] NOT_LOGGED_ALLOWED_PATHS = {
            USER_CONTROLLER_PATH,
            SPORT_CONTROLLER_PATH,
            RULE_CONTROLLER_PATH,
            "/api/teams/**",
            "/api/tournaments/**",
            "/api/games/**",
            "/api/rules/**",
    };
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String[] AUTH_PATHS = { "/api/auth/authenticate", "/api/auth/register" };
    private static final String[] ALLOWED_METHODS = { "GET", "POST", "PUT", "DELETE", "OPTIONS" };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requestMatcherRegistry ->
                        requestMatcherRegistry
                                .requestMatchers(AUTH_PATHS).permitAll()
                                .requestMatchers(SWAGGER_PATHS).permitAll()
                                .requestMatchers(HttpMethod.GET, NOT_LOGGED_ALLOWED_PATHS).permitAll()
                                .requestMatchers(HttpMethod.POST, SPORT_CONTROLLER_PATH).hasAnyRole(ADMIN_ROLE)
                                .requestMatchers(HttpMethod.PUT, SPORT_CONTROLLER_PATH).hasAnyRole(ADMIN_ROLE)
                                .requestMatchers(HttpMethod.DELETE, SPORT_CONTROLLER_PATH).hasAnyRole(ADMIN_ROLE)
                                .requestMatchers(RULE_CONTROLLER_PATH).hasAnyRole(ADMIN_ROLE)
                                .requestMatchers(HttpMethod.POST, USER_CONTROLLER_PATH).hasAnyRole(ADMIN_ROLE)
                                .requestMatchers(HttpMethod.PUT, USER_CONTROLLER_PATH).hasAnyRole(ADMIN_ROLE)
                                .requestMatchers(HttpMethod.DELETE, USER_CONTROLLER_PATH).hasAnyRole(ADMIN_ROLE)
                                .anyRequest().authenticated())
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler))
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public WebMvcConfigurer corsMappingConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods(ALLOWED_METHODS);
            }
        };
    }
}

