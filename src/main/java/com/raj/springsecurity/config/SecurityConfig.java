package com.raj.springsecurity.config;


import com.raj.springsecurity.authPackage.AuthenticatinSuccessHandler;
import com.raj.springsecurity.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.beans.Encoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(register->{
            register.requestMatchers("/home").permitAll();
            register.requestMatchers("/register/**").permitAll();
            register.requestMatchers("/admin/**").hasRole("ADMIN");
            register.requestMatchers("/user/**").hasRole("USER");
            register.anyRequest().authenticated();

        })/*.formLogin(httpSecurityFormLoginConfigurer -> {
            httpSecurityFormLoginConfigurer
                    .loginPage("/login")
                    .successHandler(new AuthenticatinSuccessHandler())
                    .permitAll();
                })*/
                .build();
    }

   /* @Bean
    public UserDetailsService userDetailsService(){
        UserDetails normalUser = User.builder()
                .username("reeta")
                .password("raj")
                .roles("USER")
                .build();
        UserDetails adminUser = User.builder()
                .username("raj")
                .password("reeta")
                .roles("ADMIN","USER")
                .build();
        return new InMemoryUserDetailsManager(adminUser,normalUser);
    }*/

    @Bean
    public UserDetailsService userDetailsService(){
        return userDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


}
