package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    private Integer id;
    @Column
    @NotEmpty(message = "Empty position")
    private String position;
    @Column
    @PositiveOrZero(message = "Salary must ne positive")
    @NotNull(message = "Empty salary")
    private Double salary;



    public Employee(String position, Double salary) {
        this.position = position;
        this.salary = salary;
    }

    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser myUser;


}
