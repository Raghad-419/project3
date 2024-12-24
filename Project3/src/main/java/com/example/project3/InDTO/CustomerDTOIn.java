package com.example.project3.InDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTOIn {
    @NotEmpty(message = "Empty name")
    @Size(min = 4,max = 10,message = "Length of username must be between 4 and 10 characters")
    private String username;
    @NotEmpty(message = "Empty password")
    @Size(min = 6,message = "Password must be at least 6 characters")
    private String password;
    @NotEmpty(message = "Empty name")
    @Size(min = 2,max = 20,message = "Name length must be between 2 and 20 characters")
    private String name;
    @Email
    @NotEmpty(message = "Empty email")
    private String email;
    @NotEmpty(message = "Empty phone number")
    @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with 05 and be 10 digits")
    private String phoneNum;
}
