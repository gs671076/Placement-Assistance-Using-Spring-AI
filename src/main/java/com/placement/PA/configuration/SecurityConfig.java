package com.placement.PA.configuration;

import com.placement.PA.services.PlacementDetailsService;
import com.placement.PA.services.StudentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private StudentDetailsService studentDetailsService;

    @Autowired
    private PlacementDetailsService placementDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider studentAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(studentDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider placementAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(placementDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // -------------------------------------------------------
    // Filter chain 1: Placement Authority  (higher priority)
    // Covers /Placement/** routes — uses /login-placement form
    // -------------------------------------------------------
    @Bean
    @Order(1)
    public SecurityFilterChain placementFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/Placement/**", "/login-placement", "/process-placementLogin", "/logoutPA")
            .authenticationProvider(placementAuthProvider())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login-placement").permitAll()
                .requestMatchers("/Placement/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login-placement")
                .loginProcessingUrl("/process-placementLogin")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/Placement/dashboard", true)
                .failureUrl("/login-placement?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logoutPA"))
                .logoutSuccessUrl("/login-placement?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }

    // -------------------------------------------------------
    // Filter chain 2: Student  (default / lower priority)
    // Covers /student/** routes — uses /login form
    // -------------------------------------------------------
    @Bean
    @Order(2)
    public SecurityFilterChain studentFilterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(studentAuthProvider())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/Register", "/add-student",
                    "/login", "/about",
                    "/css/**", "/img/**", "/javascript/**"
                ).permitAll()
                .requestMatchers("/student/**").hasRole("STUDENT")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/processLogin")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/student/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }
}
