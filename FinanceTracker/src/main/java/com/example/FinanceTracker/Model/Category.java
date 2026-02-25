package com.example.FinanceTracker.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name; 

    @NotNull
    @Column(name = "type_id")   // maps to the column in the database
    private int typeId;          // 1 = INCOME, 2 = EXPENSE

}