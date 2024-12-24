package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Empty account number")
    @Pattern(regexp = "\\b\\d{4}-\\d{4}-\\d{4}-\\d{4}\\b",
            message = "Account number must follow the format XXXX-XXXX-XXXX-XXXX.")
    private String accountNum;
    @Column
    @NotNull(message = "Empty balance")
    @PositiveOrZero(message = "Balance must be positive")
    private Double balance;
    @Column(columnDefinition = "BOOLEAN not null default false")
    private Boolean isActive= false;


    @ManyToOne
    @JsonIgnore
    private Customer customer;

}
