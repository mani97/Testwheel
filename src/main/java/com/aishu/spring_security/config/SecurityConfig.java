package com.aishu.spring_security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                         .requestMatchers("/welcome","/testwheel", "/login", "/signup", "/dashboard",
                         "/assets/**", "/css/**", "/js/**","/saveWizard",
                         "/images/**","/fontawesome/**","/fonts/**",
                         "/").permitAll()
                        .anyRequest().authenticated()
                // .permitAll()
                )
                // Form login
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            String uri = request.getRequestURI();
                            if (uri.equals("/login")) {
                                request.getSession().setAttribute("loginSuccess", "Login successful!");
                                response.sendRedirect("/dashboard");
//                            } else if (uri.equals("/signup")) {
//
//                                request.getSession().setAttribute("signupSuccess", "Signup successful!");
//                                response.sendRedirect("/login"); // after signup, go to login page
                            } else {
                                response.sendRedirect("/signup"); // fallback
                            }
                        })
                            // request.getSession().setAttribute("loginSuccess", "Login
                            // successful!");//${session.loginSuccess}.
                            //response.sendRedirect("/dashboard");
                            // .defaultSuccessUrl("/dashboard", true)
                            // .permitAll()
                        //})

                        .failureHandler((request, response, exception) -> {
                            String uri = request.getRequestURI();

                            if (uri.equals("/login")) {
                                request.getSession().setAttribute("loginError", "Invalid username or password!?");
                                response.sendRedirect("/login");
                            } else if (uri.equals("/signup")) {
                                request.getSession().setAttribute("signupError", "Signup failed! Please try again.");
                                response.sendRedirect("/signup");
                            } else {
                                response.sendRedirect("/signup"); // fallback
                            }
                        })


                )
                // OAuth2 login
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true))
                // Logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        //.logoutSuccessUrl("/login?logout")
                        .permitAll())
                // Session + JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
