package com.raj.springsecurity.config;


//import com.raj.springsecurity.authPackage.AuthenticatinSuccessHandler;
//import com.raj.springsecurity.services.MyUserDetailsService;
import com.raj.springsecurity.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService myUserDetailsService;
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

                }).formLogin(AbstractAuthenticationFilterConfigurer::permitAll).build();
    }

   /* @Bean
    public UserDetailsService userDetailsService(){
        UserDetails normalUser = User.builder()
                .username("reeta")
                .password("$2a$12$7emwE8A/BIJKgswBNgASDODtGhW2w.CyOVLvvJYDghWW1Vwn5xiXu")
                .roles("USER")
                .build();
        UserDetails adminUser = User.builder()
                .username("raj")
                .password("$2a$12$7emwE8A/BIJKgswBNgASDODtGhW2w.CyOVLvvJYDghWW1Vwn5xiXu")
                .roles("ADMIN","USER")
                .build();
        return new InMemoryUserDetailsManager(adminUser,normalUser);
    }*/

    @Bean
    public UserDetailsService userDetailsService(){
        return myUserDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
