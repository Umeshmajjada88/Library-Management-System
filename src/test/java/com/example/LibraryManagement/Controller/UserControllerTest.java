package com.example.LibraryManagement.Controller;

import com.example.LibraryManagement.Security.JwtAuthFilter;
import com.example.LibraryManagement.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)

@AutoConfigureMockMvc(addFilters = false)

class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    // VERY IMPORTANT
    // Mock your JWT filter also
    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Test
    void testGetUsers() throws Exception {

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }
}