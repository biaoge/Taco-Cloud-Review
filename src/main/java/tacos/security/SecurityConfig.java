package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
/*
 * @EnableWebSecurity and extending WebSecurityConfigurerAdapter are deprecated in Spring Security 5.7.0-M2 and later, spring boot 3.x requires Spring-Security 6.x
 * Check the parent pom.xml to see the version of Spring Boot being used
 * 
 */
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// @EnableWebSecurity
public class SecurityConfig /* extends WebSecurityConfigurerAdapter */ {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().authenticated())
                .formLogin();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("buzz")
                        .password("{noop}infinity")
                        .roles("USER")
                        .build(),
                User.withUsername("woody")
                        .password("{noop}bullseye")
                        .roles("USER")
                        .build());
    }
}
