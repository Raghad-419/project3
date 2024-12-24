package com.example.project3.Service;

import com.example.project3.ApiResponse.ApiException;
import com.example.project3.InDTO.EmployeeDTOIn;
import com.example.project3.Model.Employee;
import com.example.project3.Model.MyUser;
import com.example.project3.OutDTO.EmployeeDTO;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
private final EmployeeRepository employeeRepository;
private final AuthRepository authRepository;


//for admin only
    public List<EmployeeDTO> getAllEmployees(Integer userId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if(myUser==null){
            throw new ApiException("Wrong user id");
        }
        List<MyUser> employees =authRepository.findAllByRole("EMPLOYEE");
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for(MyUser employee : employees){
            EmployeeDTO employeeDTO = new EmployeeDTO(employee.getName(),employee.getEmployee().getPosition(),employee.getEmployee().getSalary(),employee.getEmail());
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }

    //for employee
    public void updateEmployee(Integer userId, EmployeeDTOIn employeeDTOIn){
        Employee oldEmployee = employeeRepository.findEmployeeById(userId);
        if(oldEmployee==null){
            throw new ApiException("Wrong employee id");
        }

        oldEmployee.setPosition(employeeDTOIn.getPosition());
        oldEmployee.setSalary(employeeDTOIn.getSalary());

        // Fetch the associated MyUser object
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) {
            throw new ApiException("Associated user not found for the employee ID: " + userId);
        }
       myUser.setUsername(employeeDTOIn.getUsername());
       myUser.setName(employeeDTOIn.getName());
       myUser.setEmail(employeeDTOIn.getEmail());

        // Check if the password needs to be updated
        if (employeeDTOIn.getPassword() != null && !employeeDTOIn.getPassword().isEmpty()) {
            String hashPassword = new BCryptPasswordEncoder().encode(employeeDTOIn.getPassword());
            myUser.setPassword(hashPassword);
        }


        oldEmployee.setMyUser(myUser);
        authRepository.save(myUser);

    }



    public void deleteEmployee(Integer userId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) {
            throw new ApiException("Associated user not found for the employee ID: " + userId);
        }
        Employee employee = employeeRepository.findEmployeeById(userId);
        if(employee==null){
            throw new ApiException("Wrong employee id");
        }

        authRepository.delete(myUser);
    }




}
