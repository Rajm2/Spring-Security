package com.raj.springsecurity.services;

import com.raj.springsecurity.model.MyUser;
import com.raj.springsecurity.repositories.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MyUserRepository myUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<MyUser> user = myUserRepository.findByUsername(username);
        if (user.isPresent()){
            MyUser usr = user.get();
            UserDetails userDetails = User.builder()
                    .username(usr.getUsername())
                    .password(usr.getPassword())
                    .roles(getRole(usr))
                    .build();
            return userDetails;
        }else {
            throw new UsernameNotFoundException(username);
        }
    }
    private String[] getRole(MyUser user){
        if(user.getRole() == null){
            return new String[]{"USER"};
        }
        return user.getRole().split(",");
    }
}
