package io.github.hoongjian_0w0.cmsback.security.config;

import io.github.hoongjian_0w0.cmsback.security.filter.JwtAuthenticationTokenFilter;
import io.github.hoongjian_0w0.cmsback.security.handler.AnonymousAuthenticationHandler;
// import io.github.hoongjian_0w0.cmsback.security.handler.LoginFailureHandler;
import io.github.hoongjian_0w0.cmsback.security.handler.UserAccessDeniedHanlder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private UserAccessDeniedHanlder userAccessDeniedHanlder;

    // @Autowired
    // private LoginFailureHandler loginFailureHandler;

    @Autowired
    private AnonymousAuthenticationHandler anonymousAuthenticationHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * During login, you need to call AuthenticationManager.authenticate to perform authentication once.
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable CSRF protection
        http.csrf(csrf -> csrf.disable());

        /*http.formLogin(configurer -> {
            configurer
                .failureHandler(loginFailureHandler);
        });
*/
        http.sessionManagement(configurer -> {
            configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        // Configure request authorization rules
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers("/auth/login") // allow access to login path
                        .permitAll()
                        .anyRequest() // all other requests
                        .authenticated() // require authentication
        );

        // Add JWT filter before the default UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // Add Exception Handler
        http.exceptionHandling(configurer -> {
            configurer.accessDeniedHandler(userAccessDeniedHanlder)
            .authenticationEntryPoint(anonymousAuthenticationHandler);
        });

        return http.build();
    }

}
