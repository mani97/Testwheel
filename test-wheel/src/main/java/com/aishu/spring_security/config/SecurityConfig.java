package com.aishu.spring_security.config;

import com.aishu.spring_security.config.JwtFilter;
import com.aishu.spring_security.service.CookieUtil;
import com.aishu.spring_security.service.JwtStoreService;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JwtStoreService jwtStoreService;

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                        "/favicon.png","/welcome","/testwheel", "/login", "/signup","/verify-otp",
                                        "/assets/**", "/css/**", "/js/**","/createtest2","/forgot-password-phone",
                                        "/images/**","/fontawesome/**","/fonts/**","/timeout","/timeout",
                                        "/","/perform_login","/saveWizard","/favicon.png","/createtest-2").permitAll()
                                .anyRequest().authenticated()
                )
                // Form login
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/dashboard", true)    // redirect after success
                        .failureUrl("/login?error=true")          // redirect on failure
                        .permitAll()
                        .failureHandler((request, response, exception) -> {
                            request.getSession().setAttribute("loginError", "Invalid Username or Password!!");
                            response.sendRedirect("/login");
                        })
                )
                // OAuth2 login
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true))
                // Logout
                .logout(logout -> logout
                        .logoutUrl("/logout")                // custom logout URL
                        .logoutSuccessUrl("/login?logout=true")      // redirect after logout
                        .deleteCookies("JSESSIONID", "accessToken")  // clear cookies
                        .invalidateHttpSession(true)                 // invalidate session
                        .permitAll()
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("/login?logout=true");
                        }))
                // Session +
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionUrl("/timeout")
                        .maximumSessions(1) // restrict concurrent logins per user
                        .expiredUrl("/timeout"))
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        .ignoringRequestMatchers("/perform_login", "/perform_logout", "/saveWizard", "/api/**", "/images/**", "/assets/**")
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
