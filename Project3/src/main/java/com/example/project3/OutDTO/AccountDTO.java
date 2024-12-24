package com.example.project3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private String customer_name;
    private String accountNum;
    private Double balance;
    private Boolean isActive;
}
