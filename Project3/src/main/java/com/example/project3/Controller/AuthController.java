package com.example.project3.Controller;

import com.example.project3.ApiResponse.ApiResponse;
import com.example.project3.InDTO.CustomerDTOIn;
import com.example.project3.InDTO.EmployeeDTOIn;
import com.example.project3.Model.MyUser;
import com.example.project3.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register-customer")
    public ResponseEntity RegisterUser(@RequestBody @Valid CustomerDTOIn customerDTOIn){
        authService.RegisterCustomer(customerDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("User RegisterUser"));
    }

    @PostMapping("/register-employee")
    public ResponseEntity RegisterEmployee(@RequestBody @Valid EmployeeDTOIn employeeDTOIn){
        authService.RegisterEmployee(employeeDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("User RegisterUser"));
    }

}
