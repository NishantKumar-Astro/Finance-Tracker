package com.example.FinanceTracker.Service;

import com.example.FinanceTracker.Model.Users;
import com.example.FinanceTracker.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private UserService userService;

    private Users testUser;
    private PasswordRequest loginRequest;

    @BeforeEach
    void setUp() {
        testUser = new Users();
        testUser.setId(1L);
        testUser.setUsername("john");
        testUser.setEmail("john@example.com");
        testUser.setPassword("encodedPassword");
        testUser.setRole("Role_USER");

        loginRequest = new PasswordRequest();
        loginRequest.setUsername("john");
        loginRequest.setPassword("rawPassword");
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        List<Users> result = userService.getAllUsers();
        assertThat(result).hasSize(1).contains(testUser);
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        Users result = userService.getUserById(1L);
        assertThat(result).isEqualTo(testUser);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        Users result = userService.getUserById(99L);
        assertThat(result).isNull();
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(Users.class))).thenAnswer(inv -> inv.getArgument(0));
        Users input = new Users();
        input.setUsername("alice");
        input.setPassword("rawPass");
        input.setEmail("alice@example.com");
        Users saved = userService.createUser(input);
        assertThat(saved.getPassword()).startsWith("$2a$"); // BCrypt hash
        assertThat(saved.getRole()).isEqualTo("Role_USER");
        verify(userRepository).save(any(Users.class));
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        // We need to mock encoder.matches – but it's a private field; we can't mock it directly.
        // Instead, we can use a real encoder and set a known password.
        // For simplicity, we'll set the password to a known hash and use real encoder.
        // But we are mocking userRepository, so we can set the password on testUser.
        // Let's use a real encoder to generate a hash for "rawPassword".
        String rawPassword = "rawPassword";
        String encoded = new BCryptPasswordEncoder(12).encode(rawPassword);
        testUser.setPassword(encoded);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).delete(testUser);

        boolean result = userService.deleteUser(1L, rawPassword);
        assertThat(result).isTrue();
        verify(userRepository).delete(testUser);
    }

    @Test
    void testDeleteUser_WrongPassword() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        // testUser has a dummy encoded password, not matching "wrong"
        boolean result = userService.deleteUser(1L, "wrong");
        assertThat(result).isFalse();
        verify(userRepository, never()).delete(any());
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        boolean result = userService.deleteUser(99L, "any");
        assertThat(result).isFalse();
        verify(userRepository, never()).delete(any());
    }

    @Test
    void testVerify_ValidCredentials() {
        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);
        when(jwtService.generateToken("john")).thenReturn("jwt-token");

        String token = userService.verify(loginRequest);
        assertThat(token).isEqualTo("jwt-token");
    }

    @Test
    void testVerify_InvalidCredentials() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Bad credentials"));

        String result = userService.verify(loginRequest);
        assertThat(result).startsWith("Fail:");
    }
}