package com.example.FinanceTracker.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double amount;
    private LocalDate date;

    // Many transactions can belong to ONE category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Many transactions can belong to ONE user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}