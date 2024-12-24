package com.example.project3.InDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTOIn {
    @NotNull(message = "Empty balance")
    @PositiveOrZero(message = "Balance must be positive")
    private Double balance;
    private Boolean isActive;
    private String accountNum; // Optional, used only for validation purposes


}
