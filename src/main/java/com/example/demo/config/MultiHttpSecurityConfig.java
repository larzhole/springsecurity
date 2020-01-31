package com.example.demo.config;

import com.example.demo.security.JwtFilter;
import com.example.demo.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class MultiHttpSecurityConfig {

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Configuration
    public static class HttpSecurityConfig extends WebSecurityConfigurerAdapter {
        private JwtProvider jwtProvider;

        @Autowired
        public HttpSecurityConfig(JwtProvider jwtProvider) {
            this.jwtProvider = jwtProvider;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors()
                    .and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("actuator/*", "/security").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

        @Bean
        @Override
        protected AuthenticationManager authenticationManager() throws Exception {
            return super.authenticationManager();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            final CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedHeaders(Arrays.asList(
                    "Content-Type",
                    "Access-Control-Allow-Origin",
                    "Access-Control-Allow-Headers",
                    "Origin",
                    "Accept",
                    "X-Requested-With",
                    "Access-Control-Request-Method",
                    "Access-Control-Request-Headers",
                    "Authorization",
                    "Pragma",
                    "pragma",
                    "cache-control",
                    "expires"
            ));
            configuration.setAllowedOrigins(Collections.singletonList("*"));
            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            configuration.setAllowCredentials(true);
            configuration.setMaxAge(120L);

            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
    }

}
