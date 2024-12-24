package com.example.project3.Controller;

import com.example.project3.ApiResponse.ApiResponse;
import com.example.project3.InDTO.EmployeeDTOIn;
import com.example.project3.Model.MyUser;
import com.example.project3.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/get-all-employee")
    public ResponseEntity getAllEmployees(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(employeeService.getAllEmployees(myUser.getId()));
    }

@PutMapping("/update-employee")
    public ResponseEntity updateEmployee(@AuthenticationPrincipal MyUser myUser, @RequestBody @Valid EmployeeDTOIn employeeDTOIn){
        employeeService.updateEmployee(myUser.getId(),employeeDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Employee updated"));
    }

@DeleteMapping("/delete-employee")
public ResponseEntity deleteEmployee(@AuthenticationPrincipal MyUser myUser){
        employeeService.deleteEmployee(myUser.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Employee deleted"));
}

}
