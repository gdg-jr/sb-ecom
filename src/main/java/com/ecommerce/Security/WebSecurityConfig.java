package com.ecommerce.Security;

import com.ecommerce.Security.jwt.AuthEntryPointJWT;
import com.ecommerce.Security.jwt.AuthTokenFilter;
import com.ecommerce.Security.services.UserDetailsImpl;
import com.ecommerce.Security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthEntryPointJWT unauthorizedHandler;
    @Autowired
    private AuthEntryPointJWT unAuth;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(){
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        ///this is to remove the authentication for the h2 database login poge
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").permitAll()
                .requestMatchers("/images/**").permitAll()
                .anyRequest().authenticated());
        ///Form-based login (the /login page). Uncomment to enable it again, for testing.
        //http.formLogin(Customizer.withDefaults());
        ///This is the basic popup of the browser.
        //http.httpBasic(Customizer.withDefaults());                 // HTTP Basic authentication (active)
        ///this is to hide the cookies
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        /// optional, for Postman testing
        http.csrf(AbstractHttpConfigurer::disable);
        ///To point the code to go the custom defined Unauthorised handler in place of the default one.
        http.exceptionHandling(exception->exception.authenticationEntryPoint(unAuth));
        /// For getting the h2 database frame come up.
        http.headers(headers->
                headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        http.authenticationProvider(authenticationProvider());
        ///Adding the custom filter to autheticate user before going into the usernamePasswordAuthenticateFilter default class.
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web-> web.ignoring().requestMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resource/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"
        ));
    }
}
