package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MyUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(10) not null unique")
    @NotEmpty(message = "Empty name")
    @Size(min = 4,max = 10,message = "Length of username must be between 4 and 10 characters")
    private String username;
    @Column
    @NotEmpty(message = "Empty password")
    @Size(min = 6,message = "Password must be at least 6 characters")
    private String password;
    @Column
    @NotEmpty(message = "Empty name")
    @Size(min = 2,max = 20,message = "Name length must be between 2 and 20 characters")
    private String name;
    @Column(nullable = false, unique = true)
    @Email
    @NotEmpty(message = "Empty email")
    private String email;
    @Column
    @Pattern(regexp = "CUSTOMER|EMPLOYEE|ADMIN",message = "role Must be either CUSTOMER , EMPLOYEE or ADMIN only")
    private String role;

    public MyUser(Integer id, String username, String password, String name, String email, String role ,Customer customer) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.customer = customer;
    }

    public MyUser(Integer id, String username, String password, String name, String email, String role ,Employee employee) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.employee = employee;
    }

    @OneToOne(mappedBy = "myUser", cascade = CascadeType.ALL)
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private Customer customer;

    @OneToOne(mappedBy = "myUser", cascade = CascadeType.ALL)
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private Employee employee;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
