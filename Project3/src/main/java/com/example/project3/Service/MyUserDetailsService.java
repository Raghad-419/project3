package com.example.project3.Service;

import com.example.project3.ApiResponse.ApiException;
import com.example.project3.Model.MyUser;
import com.example.project3.Repository.AuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    public MyUserDetailsService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = authRepository.findMyUserByUsername(username);
        if(myUser==null){
            throw new ApiException("Wrong username or password");
        }
        return myUser;
    }
}
