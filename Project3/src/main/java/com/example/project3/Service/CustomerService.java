package com.example.project3.Service;

import com.example.project3.ApiResponse.ApiException;
import com.example.project3.InDTO.CustomerDTOIn;
import com.example.project3.Model.Customer;
import com.example.project3.Model.MyUser;
import com.example.project3.OutDTO.CustomerDTO;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;

    //for admin only
    public List<CustomerDTO> getAllCustomers(Integer userId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if(myUser==null){
            throw new ApiException("Wrong user id");
        }

        List<MyUser> customers = authRepository.findAllByRole("CUSTOMER");
        List<CustomerDTO> customerDTOS=new ArrayList<>();

        for(MyUser customer:customers){
            CustomerDTO customerDTO = new CustomerDTO(customer.getName(),customer.getCustomer().getPhoneNum(),customer.getEmail());
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }


    public void updateCustomer(Integer userId, CustomerDTOIn customerDTOIn){
        Customer oldCustomer = customerRepository.findCustomerById(userId);
        if(oldCustomer==null){
            throw new ApiException("Wrong customer id");
        }
        oldCustomer.setPhoneNum(customerDTOIn.getPhoneNum());

        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) {
            throw new ApiException("Associated user not found for the customer ID: " + userId);
        }
        myUser.setName(customerDTOIn.getName());
        myUser.setUsername(customerDTOIn.getUsername());
        myUser.setEmail(customerDTOIn.getEmail());

        if (customerDTOIn.getPassword() != null && !customerDTOIn.getPassword().isEmpty()) {
            String hashPassword = new BCryptPasswordEncoder().encode(customerDTOIn.getPassword());
            myUser.setPassword(hashPassword);
        }

        oldCustomer.setMyUser(myUser);
        authRepository.save(myUser);


    }


    public void deleteCustomer(Integer userId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) {
            throw new ApiException("Associated user not found for the customer ID: " + userId);
        }
        Customer customer = customerRepository.findCustomerById(userId);
        if(customer==null){
            throw new ApiException("Wrong customer id");
        }
        authRepository.delete(myUser);
    }



}
