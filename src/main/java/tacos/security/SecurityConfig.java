package tacos.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
/*
 * @EnableWebSecurity and extending the WebSecurityConfigurerAdapter class are deprecated in Spring Security 5.7.0-M2 and later. 
 * Spring boot 3.x (the version we are using) requires Spring-Security 6.x, so don't need to use these anymore.
 * Check the <parent></parent> section of pom.xml to see the version of Spring Boot being used
 * 
 */
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> 
                authz.anyRequest().authenticated()
            )
            .formLogin();
        return http.build();
    }

    /*
     * 4.2.1 In-memory user store
     * In a real application, you would likely use a database or another user store.
     */
    /*
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
    */

    /*
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
        .inMemoryAuthentication()
        .withUser("buzz")
        .password("{noop}infinity")
        .roles("USER")
        .and()
        .withUser("woody")
        .password("{noop}bullseye")
        .roles("USER");
    }
    */



    /*
     * 4.2.2 JDBC-based user store
     * DataSource bean is auto-configured by Spring Boot. How it works:
     * 1. Spring Boot scans the project's classpath. It sees that we have: 
     *  - spring-boot-starter-data-jdbc (or data-jpa) in pom.xml.
     *  - The H2 database driver (com.h2database:h2) in pom.xml.
     *  - Database connection properties (spring.datasource.*) in  application.properties.
     * 2.  Based on these clues, Spring Boot's auto-configuration automatically creates and configures a DataSource bean and wires it to here.
     */
    
     /*
    @Bean
    public UserDetailsService jdbcUserDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager users  = new JdbcUserDetailsManager(dataSource);

        users.setUsersByUsernameQuery("select username, password, enabled from Users " +
        "where username=?");

        users.setAuthoritiesByUsernameQuery(
            "select username, authority from UserAuthorities " +
            "where username=?");

        return users;
    }
    */    

    // for testing purpose only, no encryption for passwords, just stroe it in plain text
    /*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    */

    /*
    @Autowired
    public void configure(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
        auth
        .jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery("select username, password, enabled from Users where username=?")
        .authoritiesByUsernameQuery("select username, authority from UserAuthorities where username=?")
        .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
    */


    
    // 4.2.3 LDAP-based user store
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
        .ldapAuthentication()
        .userSearchBase("ou=people")
        .userSearchFilter("(uid={0})")
        .groupSearchBase("ou=groups")
        .groupSearchFilter("(member={0})")
        .passwordCompare()
        .passwordEncoder(NoOpPasswordEncoder.getInstance())
        .passwordAttribute("userPassword")
        .and()
        .contextSource()
        .url("ldap://localhost:8389/dc=tacocloud,dc=com");
        
    }
    
}
