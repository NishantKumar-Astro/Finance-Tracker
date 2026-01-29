package com.example.FinanceTracker.Service;

import com.example.FinanceTracker.Model.User;
import com.example.FinanceTracker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository Repo;

    public List<User>
    getAllUsers() {
        return Repo.findAll();
    }

    public User
    getUserById(Long id) {
        return Repo.findById(id).orElse(null);
    }

    public User
    createUser(User user) {
        return Repo.save(user);
    }

    public boolean
    deleteUser(Long id) {
        if (Repo.existsById(id)) {
            Repo.deleteById(id);
            return true;
        }
        return false;
    }
}