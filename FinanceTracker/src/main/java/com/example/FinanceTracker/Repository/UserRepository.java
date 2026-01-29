package com.example.FinanceTracker.Repository;

import com.example.FinanceTracker.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
