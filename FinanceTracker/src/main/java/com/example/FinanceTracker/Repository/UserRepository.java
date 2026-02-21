package com.example.FinanceTracker.Repository;

import com.example.FinanceTracker.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {
    Users findByUsername(String username);
    Users findByEmail(String email);
}
