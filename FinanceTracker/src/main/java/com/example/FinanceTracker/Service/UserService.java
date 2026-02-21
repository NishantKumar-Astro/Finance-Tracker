package com.example.FinanceTracker.Service;

import com.example.FinanceTracker.Model.Users;
import com.example.FinanceTracker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authmanager;

    @Autowired
    private UserRepository Repo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public List<Users>
    getAllUsers() {
        return Repo.findAll();
    }

    public Users
    getUserById(Long id) {
        return Repo.findById(id).orElse(null);
    }

    public Users
    createUser(Users users) {
        users.setPassword(encoder.encode(users.getPassword()));
        users.setRole("ROLE_USER"); // Set a default role
        return Repo.save(users);
    }

    public boolean
    deleteUser(Long id, String email,String password) {
        if (Repo.existsById(id) && encoder.matches(password,getUserById(id).getPassword())) {
            Repo.deleteById(id);
            return true;
        }
        return false;
    }


    public String verify(Users user) {
        try {
            Authentication authentication = authmanager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getUsername());
            }
        } catch (Exception e) {
            return "Fail: " + e.getMessage();
        }
        return "Fail: Not Authenticated";
    }



}