package com.example.FinanceTracker.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @NotNull
    private CategoryType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users users;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    @ToString.Exclude
    private List<Transaction> transactions = new ArrayList<>();

    public enum CategoryType { INCOME, EXPENSE }
}