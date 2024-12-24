package com.example.project3.InDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTOIn {
//    @NotEmpty(message = "Empty name")
//    @Size(min = 4,max = 10,message = "Length of username must be between 4 and 10 characters")
    private String username;
//    @NotEmpty(message = "Empty password")
//    @Size(min = 6,message = "Password must be at least 6 characters")
    private String password;
//    @NotEmpty(message = "Empty name")
//    @Size(min = 2,max = 20,message = "Name length must be between 2 and 20 characters")
    private String name;
//    @Email
//    @NotEmpty(message = "Empty email")
    private String email;
   // @NotEmpty(message = "Empty position")
    private String position;
//    @PositiveOrZero(message = "Salary must ne positive")
//    @NotNull(message = "Empty salary")
    private Double salary;
}
