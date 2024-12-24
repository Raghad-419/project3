package com.example.project3.Controller;

import com.example.project3.ApiResponse.ApiResponse;
import com.example.project3.InDTO.CustomerDTOIn;
import com.example.project3.Model.MyUser;
import com.example.project3.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/get-all-customers")
    public ResponseEntity getAllCustomers(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(customerService.getAllCustomers(myUser.getId()));
    }

    @PutMapping("/update-customer")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal MyUser myUser, @RequestBody @Valid CustomerDTOIn customerDTOIn){
        customerService.updateCustomer(myUser.getId(),customerDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Customer updated"));
    }

    @DeleteMapping("/delete-customer")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal MyUser myUser){
        customerService.deleteCustomer(myUser.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Customer deleted"));
    }
}
