package com.challenge.disneyworld.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    private static final String[] SWAGGER_AUTH = {
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };
    private static final String[] H2_AUTH = {
            "/h2-console/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(SWAGGER_AUTH).permitAll()
                .antMatchers(H2_AUTH).permitAll()
                .antMatchers("/auth/login").permitAll()
                .anyRequest().authenticated();

        // HttpBasic authentication
        http.httpBasic();

        // This is to make the h2-console work
        http.headers().frameOptions().disable();

        // This is to allow logouts
        http.logout().permitAll();

        // This is to make the session stateless.
        // I experienced that without this config, the server sends a set-cookie response
        // to the client.
        // Then, in subsequent calls the client uses this cookie to "authenticate", making
        // pointless the "authorization" header in the request.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // The following is to display the default login page
        // http.formLogin();
        // http.exceptionHandling().accessDeniedPage("/login");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
