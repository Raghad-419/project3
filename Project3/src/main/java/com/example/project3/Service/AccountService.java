package com.example.project3.Service;

import com.example.project3.ApiResponse.ApiException;
import com.example.project3.InDTO.AccountDTOIn;
import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Model.MyUser;
import com.example.project3.OutDTO.AccountDTO;
import com.example.project3.Repository.AccountRepository;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;


    public List<AccountDTO> getMyAccount(Integer userId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if(myUser==null){
            throw new ApiException("Associated user not found for the customer ID: " + userId);
        }
        Customer customer = customerRepository.findCustomerById(userId);
        if(customer==null){
            throw new ApiException("Wrong customer id");
        }
       List<Account> accounts =  accountRepository.findAllByCustomer(customer);
        List<AccountDTO> accountDTOS =new ArrayList<>();
        for(Account account :accounts){
            AccountDTO accountDTO = new AccountDTO(account.getCustomer().getMyUser().getName(),account.getAccountNum(),account.getBalance(),account.getIsActive());
            accountDTOS.add(accountDTO);
        }
        return accountDTOS;
    }


    public void addAccount(Integer userId, Account account){
        MyUser myUser = authRepository.findMyUserById(userId);
        if(myUser==null){
            throw new ApiException("Associated user not found for the customer ID: " + userId);
        }
        Customer customer = customerRepository.findCustomerById(userId);
        if(customer==null){
            throw new ApiException("Wrong customer id");
        }

        account.setCustomer(customer);
        accountRepository.save(account);

    }
//for customer
    public void updateAccount(Integer userId, Integer accountId , AccountDTOIn account){
        MyUser myUser = authRepository.findMyUserById(userId);
        if(myUser ==null){
            throw new ApiException("User not found");
        }
        Customer customer = customerRepository.findCustomerById(userId);
        if(customer==null){
            throw new ApiException("Customer not found");
        }
        Account oldAccount = accountRepository.findAccountById(accountId);
        if(oldAccount==null){
            throw new ApiException("Account not found");
        }

        if(oldAccount.getCustomer().getId() != myUser.getId()){
            throw new ApiException("This is not your account");
        }

        // Validate that the account number is not being updated
        if (account.getAccountNum() != null &&
                !account.getAccountNum().equals(oldAccount.getAccountNum())) {
            throw new ApiException("You can't update the account number" +
                    " if you want new account number you can add new account");
        }
        if(!oldAccount.getIsActive()&& account.getIsActive()){
            throw new ApiException("You cannot activate the account, only the admin can do that.");
        }
        oldAccount.setBalance(account.getBalance());
        oldAccount.setIsActive(account.getIsActive());

        accountRepository.save(oldAccount);
    }

//for Customer and admin
    public void deleteAccount(Integer userId, Integer accountId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if(myUser ==null){
            throw new ApiException("User not found");
        }
        Account account = accountRepository.findAccountById(accountId);
        if(account==null){
            throw new ApiException("Account not found");
        }
        if(account.getCustomer().getId()!= myUser.getId() && !myUser.getRole().equals("ADMIN")){
            throw new ApiException("You can't delete account");
        }

        accountRepository.delete(account);
    }

    //for admin only
    public void activeAccount(Integer userId,Integer accountId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if(myUser ==null){
            throw new ApiException("User not found");
        }
        Account account = accountRepository.findAccountById(accountId);
        if(account==null){
            throw new ApiException("Account not found");
        }

        if(account.getIsActive()){
            throw new ApiException("Account already active");
        }
        account.setIsActive(true);
        accountRepository.save(account);
    }

    //for admin only
    public List<AccountDTO> getAllAccounts(Integer userId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if(myUser ==null){
            throw new ApiException("User not found");
        }
        List<Account> accounts =  accountRepository.findAll();
        List<AccountDTO> accountDTOS =new ArrayList<>();
        for(Account account :accounts){
            AccountDTO accountDTO = new AccountDTO(account.getCustomer().getMyUser().getName(),account.getAccountNum(),account.getBalance(),account.getIsActive());
            accountDTOS.add(accountDTO);
        }
        return accountDTOS;
    }

    //for customer
    public void deposit(Integer userId, Integer accountId, Double amount){
        MyUser myUser = authRepository.findMyUserById(userId);
        if(myUser==null){throw new ApiException("user not found");}

        Customer customer = customerRepository.findCustomerById(userId);
        if(customer ==null){throw new ApiException("Customer not found");}

        Account account = accountRepository.findAccountById(accountId);
        if(account==null){throw new ApiException("Account not found");}

        if(account.getCustomer().getId() != myUser.getId()){
            throw new ApiException("this is not your account ");}

        if(!account.getIsActive()){
            throw new ApiException("Account should be active");}

        account.setBalance(account.getBalance()+amount);
        accountRepository.save(account);

    }
    //for customer
    public void withdraw(Integer userId, Integer accountId, Double amount){
        MyUser myUser = authRepository.findMyUserById(userId);
        if(myUser==null){throw new ApiException("user not found");}

        Customer customer = customerRepository.findCustomerById(userId);
        if(customer ==null){throw new ApiException("Customer not found");}

        Account account = accountRepository.findAccountById(accountId);
        if(account==null){throw new ApiException("Account not found");}

        if(account.getCustomer().getId() != myUser.getId()){
            throw new ApiException("this is not your account ");
        }

        if(account.getBalance()< amount){
            throw new ApiException("Not enough balance");
        }
        if(!account.getIsActive()){
            throw new ApiException("Account should be active");
        }
        account.setBalance(account.getBalance()-amount);
        accountRepository.save(account);
    }

    //for customer
public void Transfer(Integer userId, Integer accountId1 ,Integer accountId2 , Double amount){
        MyUser myUser =authRepository.findMyUserById(userId);
        if(myUser==null){throw new ApiException("user not found");}

        Customer customer = customerRepository.findCustomerById(userId);
        if(customer ==null){throw new ApiException("Customer not found");}

        Account account1 = accountRepository.findAccountById(accountId1);
        Account account2 = accountRepository.findAccountById(accountId2);
        if(account1==null||account2==null){throw new ApiException("Account not found");}

        if(account1.getCustomer().getId() != myUser.getId()){
            throw new ApiException("account with ID: "+account1.getId()+" is not your account ");
        }

        if(!account1.getIsActive()|| !account2.getIsActive()){
            throw new ApiException("Accounts should be active");
        }
        if(account1.getBalance()<amount){
            throw new ApiException("Not enough balance");
        }
        account1.setBalance(account1.getBalance()-amount);
        account2.setBalance(account2.getBalance()+amount);
        accountRepository.save(account1);
        accountRepository.save(account2);

}
        //for admin only
        public void blockAccount(Integer userId,Integer accountId){
            MyUser myUser =authRepository.findMyUserById(userId);
            if(myUser==null){throw new ApiException("user not found");}

            Account account =accountRepository.findAccountById(accountId);
            if(account==null){throw new ApiException("Account not found");}
            if(!account.getIsActive()){
                throw new ApiException("Account already blocked");
            }
            account.setIsActive(false);
            accountRepository.save(account);

        }

}
