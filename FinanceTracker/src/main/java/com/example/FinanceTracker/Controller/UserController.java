package com.example.FinanceTracker.Controller;

import com.example.FinanceTracker.Model.Users;
import com.example.FinanceTracker.Repository.UserRepository;
import com.example.FinanceTracker.Service.PasswordRequest;
import com.example.FinanceTracker.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String>
    login(@RequestBody PasswordRequest users) {
        try {
            if (users.getPassword()!=null && users.getUsername()!=null)
                return ResponseEntity.ok(userService.verify(users));
            else if (users.getUsername()==null) {
                return ResponseEntity.badRequest().body("Invalid Username or Password");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.internalServerError().body("506"); // 506 error in invisible-code/Ghost error
    }

    @PostMapping("/register")
    public ResponseEntity<String>
    createUser(@Valid @RequestBody Users users) {
        if (repo.findByEmail(users.getEmail()) == null ){
            try{
                userService.createUser(users);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } catch (Exception e){
                return ResponseEntity.internalServerError().body(e.getMessage());
            }

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
    deleteUser(@PathVariable Long id, @RequestBody PasswordRequest request) {
        return userService.deleteUser(id, request.getPassword()) ?
                ResponseEntity.ok("User deleted successfully") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}

