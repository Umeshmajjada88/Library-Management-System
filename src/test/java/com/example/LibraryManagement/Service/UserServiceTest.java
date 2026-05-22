package com.example.LibraryManagement.Service;

import com.example.LibraryManagement.DTO.AuthResponse;
import com.example.LibraryManagement.DTO.LoginRequest;
import com.example.LibraryManagement.Entity.Admin;
import com.example.LibraryManagement.Entity.Member;
import com.example.LibraryManagement.Repository.AdminRepository;
import com.example.LibraryManagement.Repository.MemberRepository;
import com.example.LibraryManagement.Security.JwtUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private UserService userService;

    private LoginRequest request;

    @BeforeEach
    void setup() {

        request = new LoginRequest(
                "admin@gmail.com",
                "9999999999"
        );
    }

    @Test
    void testLoginSuccessForAdmin() {

        Admin admin = new Admin();

        admin.setId(1L);
        admin.setName("Admin User");
        admin.setEmail("admin@gmail.com");
        admin.setPhoneNumber("9999999999");
        //admin.setRole("ADMIN");

        when(adminRepository
                .findByEmailAndPhoneNumber(
                        "admin@gmail.com",
                        "9999999999"))
                .thenReturn(Optional.of(admin));

        when(jwtUtils.generateToken(anyString(), anyString()))
                .thenReturn("fake-jwt-token");

        AuthResponse response =
                userService.login(request);

        assertNotNull(response);

        assertEquals(
                "fake-jwt-token",
                response.getToken()
        );

        assertEquals(
                "ADMIN",
                response.getRole()
        );
    }

    @Test
    void testLoginSuccessForMember() {

        Member member = new Member();

        member.setId(2L);
        member.setName("Member User");
        member.setEmail("member@gmail.com");
        member.setPhoneNumber("8888888888");
        //member.setRole("MEMBER");

        LoginRequest memberRequest =
                new LoginRequest(
                        "member@gmail.com",
                        "8888888888"
                );

        when(adminRepository
                .findByEmailAndPhoneNumber(anyString(), anyString()))
                .thenReturn(Optional.empty());

        when(memberRepository
                .findByEmailAndPhoneNumber(
                        "member@gmail.com",
                        "8888888888"))
                .thenReturn(Optional.of(member));

        when(jwtUtils.generateToken(anyString(), anyString()))
                .thenReturn("member-jwt-token");

        AuthResponse response =
                userService.login(memberRequest);

        assertNotNull(response);

        assertEquals(
                "member-jwt-token",
                response.getToken()
        );

        assertEquals(
                "MEMBER",
                response.getRole()
        );
    }

    @Test
    void testLoginFailure() {

        when(adminRepository
                .findByEmailAndPhoneNumber(anyString(), anyString()))
                .thenReturn(Optional.empty());

        when(memberRepository
                .findByEmailAndPhoneNumber(anyString(), anyString()))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> userService.login(request)
                );

        assertEquals(
                "Invalid email or phone number.",
                exception.getMessage()
        );
    }
}