package com.example.FinanceTracker.Controller;

import com.example.FinanceTracker.Model.Users;
import com.example.FinanceTracker.Repository.UserRepository;
import com.example.FinanceTracker.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository repo;

    @PostMapping("/login")
    public String
    login(@RequestBody Users users) {
        return userService.verify(users);
    }

    @PostMapping("/register")
    public ResponseEntity<Users>
    createUser(@RequestBody Users users) {
        if (repo.findByEmail(users.getEmail()) == null ){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userService.createUser(users));
        }
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                .build();
    }

    @GetMapping
    public ResponseEntity<List<Users>>
    getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users>
    getUserById(@PathVariable Long id) {
        Users users = userService.getUserById(id);
        return users != null ?
                ResponseEntity.ok(users) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteUser(@RequestBody Long id , String email, String password) {
        return userService.deleteUser(id,email,password) ?
                ResponseEntity.ok("User deleted successfully") :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found");
    }
}