package com.example.FinanceTracker.Service;

import com.example.FinanceTracker.Model.UserPrincipal;
import com.example.FinanceTracker.Model.Users;
import com.example.FinanceTracker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username);
        if (user == null) {
            System.out.println("User Not Found: " + username);
            throw new UsernameNotFoundException("user not found");
        }
        return new UserPrincipal(user);
    }
}
