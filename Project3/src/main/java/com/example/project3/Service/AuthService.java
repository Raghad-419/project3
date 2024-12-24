package com.example.project3.Service;

import com.example.project3.InDTO.CustomerDTOIn;
import com.example.project3.InDTO.EmployeeDTOIn;
import com.example.project3.Model.Customer;
import com.example.project3.Model.Employee;
import com.example.project3.Model.MyUser;
import com.example.project3.Repository.AuthRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }


    @Transactional
    public void RegisterCustomer(CustomerDTOIn customerDTOIn){

        Customer customer = new Customer(customerDTOIn.getPhoneNum());
        MyUser myUser = new MyUser(null,customerDTOIn.getUsername(),
                customerDTOIn.getPassword(),
                customerDTOIn.getName(),
                customerDTOIn.getEmail(),
                "CUSTOMER",
                customer);
        String hashPassword = new BCryptPasswordEncoder().encode(myUser.getPassword());
        myUser.setPassword(hashPassword);
        customer.setMyUser(myUser);
        authRepository.save(myUser);

    }

    public void RegisterEmployee(EmployeeDTOIn employeeDTOIn){
        Employee employee = new Employee(employeeDTOIn.getPosition(),employeeDTOIn.getSalary());
        MyUser myUser = new MyUser(null,employeeDTOIn.getUsername(),
                employeeDTOIn.getPassword(),
                employeeDTOIn.getName(),
                employeeDTOIn.getEmail(),
                "EMPLOYEE",
                employee);
        String hashPassword = new BCryptPasswordEncoder().encode(myUser.getPassword());
        myUser.setPassword(hashPassword);
        employee.setMyUser(myUser);
        authRepository.save(myUser);
    }



}
