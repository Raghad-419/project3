package com.example.project3.Repository;

import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Model.MyUser;
import com.example.project3.OutDTO.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account ,Integer> {
    Account findAccountById(Integer id);
    List<Account> findAllByCustomer(Customer customer);
}
