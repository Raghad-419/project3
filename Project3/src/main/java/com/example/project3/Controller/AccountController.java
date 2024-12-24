package com.example.project3.Controller;

import com.example.project3.ApiResponse.ApiResponse;
import com.example.project3.InDTO.AccountDTOIn;
import com.example.project3.Model.Account;
import com.example.project3.Model.MyUser;
import com.example.project3.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/get-my-account")
    public ResponseEntity getMyAccount(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(accountService.getMyAccount(myUser.getId()));
    }

    @PostMapping("/add-account")
    public ResponseEntity creatAccount(@AuthenticationPrincipal MyUser myUser, @RequestBody @Valid Account account){
        accountService.addAccount(myUser.getId(),account);
        return ResponseEntity.status(200).body(new ApiResponse("Account added"));
    }

    @PutMapping("/update-account/{accountId}")
    public ResponseEntity updateAccount(@AuthenticationPrincipal MyUser myUser , @PathVariable Integer accountId, @RequestBody @Valid AccountDTOIn account){
        accountService.updateAccount(myUser.getId(),accountId,account);
        return ResponseEntity.status(200).body(new ApiResponse("Account updated"));
    }

    @DeleteMapping("/delete-account/{accountId}")
    public ResponseEntity deleteAccount(@AuthenticationPrincipal MyUser myUser , @PathVariable Integer accountId){
        accountService.deleteAccount(myUser.getId(),accountId);
        return ResponseEntity.status(200).body(new ApiResponse("Account deleted"));

    }
    @PutMapping("/active-account/{accountId}")
    public ResponseEntity activeAccount(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer accountId){
        accountService.activeAccount(myUser.getId(),accountId);
        return ResponseEntity.status(200).body(new ApiResponse("Account Activated"));

    }
    @GetMapping("/get-all-accounts")
    public ResponseEntity getAllAccounts(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(accountService.getAllAccounts(myUser.getId()));
    }

    @PutMapping("/deposit/{accountId}/{amount}")
    public ResponseEntity deposit(@AuthenticationPrincipal MyUser myUser,@PathVariable Integer accountId, @PathVariable Double amount){
        accountService.deposit(myUser.getId(),accountId,amount);
        return ResponseEntity.status(200).body(new ApiResponse("Deposit successfully"));
    }

    @PutMapping("/withdraw/{accountId}/{amount}")
    public ResponseEntity withdraw(@AuthenticationPrincipal MyUser myUser,@PathVariable Integer accountId, @PathVariable Double amount){
        accountService.withdraw(myUser.getId(),accountId,amount);
        return ResponseEntity.status(200).body(new ApiResponse("Withdraw successfully"));
    }
@PutMapping("/transfer/{accountId1}/{accountId2}/{amount}")
    public ResponseEntity Transfer(@AuthenticationPrincipal MyUser myUser,@PathVariable Integer accountId1 ,@PathVariable  Integer accountId2 ,@PathVariable Double amount){
        accountService.Transfer(myUser.getId(),accountId1,accountId2,amount);
        return ResponseEntity.status(200).body(new ApiResponse("Transfer successfully"));
    }

    @PutMapping("/block/{accountId}")
    public ResponseEntity blockAccount(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer accountId){
        accountService.blockAccount(myUser.getId(),accountId);
        return ResponseEntity.status(200).body(new ApiResponse("Account blocked successfully"));

    }

}
