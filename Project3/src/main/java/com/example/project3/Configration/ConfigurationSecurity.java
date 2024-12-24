package com.example.project3.Configration;

import com.example.project3.Service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfigurationSecurity {

    private final MyUserDetailsService myUserDetailsService;

    public ConfigurationSecurity(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

@Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;

    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/user/**").permitAll()
                .requestMatchers("/api/v1/employee/update-employee","/api/v1/employee/delete-employee").hasAuthority("EMPLOYEE")
                .requestMatchers("/api/v1/account/get-my-account","/api/v1/account/add-account"
                        ,"/api/v1/account/update-account/**","/api/v1/account/delete-account/**"
                        ,"/api/v1/customer/delete-customer", "/api/v1/customer/update-customer"
                        ,"/api/v1/account/deposit/**","/api/v1/account/transfer/**"
                        ,"/api/v1/account/withdraw/**").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/account/active-account/**","/api/v1/account/delete-account/**"
                        ,"/api/v1/account/block/**","/api/v1/account/get-all-accounts"
                        ,"/api/v1/customer/get-all-customers","/api/v1/employee/get-all-employee").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("api/v1/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }


}
