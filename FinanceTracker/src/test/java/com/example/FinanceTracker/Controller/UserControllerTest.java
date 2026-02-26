package com.example.FinanceTracker.Controller;

import com.example.FinanceTracker.Model.Users;
import com.example.FinanceTracker.Repository.UserRepository;
import com.example.FinanceTracker.Service.JWTService;
import com.example.FinanceTracker.Service.PasswordRequest;
import com.example.FinanceTracker.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private JWTService jwtService;   // satisfies JwtFilter dependency

    @Test
    void testRegister_Success() throws Exception {
        // Manually create JSON to include password (bypass WRITE_ONLY annotation)
        String json = "{\"username\":\"test\",\"email\":\"test@example.com\",\"password\":\"secret\"}";

        when(userRepository.findByEmail("test@example.com")).thenReturn(null);
        when(userService.createUser(any(Users.class))).thenAnswer(inv -> inv.getArgument(0));

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void testRegister_EmailAlreadyExists() throws Exception {
        String json = "{\"username\":\"test\",\"email\":\"existing@example.com\",\"password\":\"secret\"}";

        when(userRepository.findByEmail("existing@example.com")).thenReturn(new Users());

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isAlreadyReported()); // 208
    }

    @Test
    void testLogin_Success() throws Exception {
        PasswordRequest request = new PasswordRequest();
        request.setUsername("john");
        request.setPassword("pass");

        when(userService.verify(any(PasswordRequest.class))).thenReturn("jwt-token");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"john\",\"password\":\"pass\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("jwt-token"));
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        Long id = 1L;
        PasswordRequest request = new PasswordRequest();
        request.setPassword("correct");

        when(userService.deleteUser(id, request.getPassword())).thenReturn(true);

        mockMvc.perform(delete("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"correct\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }

    @Test
    void testDeleteUser_NotFound() throws Exception {
        Long id = 99L;
        PasswordRequest request = new PasswordRequest();
        request.setPassword("any");

        when(userService.deleteUser(id, request.getPassword())).thenReturn(false);

        mockMvc.perform(delete("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"any\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }
}