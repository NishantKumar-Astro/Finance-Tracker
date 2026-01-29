package com.example.FinanceTracker.Controller;

import com.example.FinanceTracker.Model.User;
import com.example.FinanceTracker.Service.UserService;
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

    @GetMapping
    public ResponseEntity<List<User>>
    getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User>
    getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ?
                ResponseEntity.ok(user) :
                ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User>
    createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    eleteUser(@PathVariable Long id) {
        return userService.deleteUser(id) ?
                ResponseEntity.ok("User deleted successfully") :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found");
    }
}