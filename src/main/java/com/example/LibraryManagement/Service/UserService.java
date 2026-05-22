package com.example.LibraryManagement.Service;

import com.example.LibraryManagement.DTO.AuthResponse;
import com.example.LibraryManagement.Security.JwtUtils;
import com.example.LibraryManagement.DTO.LoginRequest;
import com.example.LibraryManagement.DTO.UserDTO;
import com.example.LibraryManagement.Entity.Admin;
import com.example.LibraryManagement.Entity.Member;
import com.example.LibraryManagement.Exception.ResourceNotFoundException;
import com.example.LibraryManagement.Repository.AdminRepository;
import com.example.LibraryManagement.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired private AdminRepository  adminRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private JwtUtils         jwtUtils;

    // ─── Register ─────────────────────────────────────────────────────────────
    public UserDTO register(UserDTO dto) {
        if ("ADMIN".equalsIgnoreCase(dto.getRole())) {
            if (adminRepository.existsByEmail(dto.getEmail()))
                throw new IllegalStateException("Admin already exists: " + dto.getEmail());
            Admin saved = adminRepository.save(
                    new Admin(dto.getName(), dto.getEmail(), dto.getPhoneNumber()));
            return adminToDTO(saved);
        } else {
            if (memberRepository.existsByEmail(dto.getEmail()))
                throw new IllegalStateException("Member already exists: " + dto.getEmail());
            Member saved = memberRepository.save(
                    new Member(dto.getName(), dto.getEmail(), dto.getPhoneNumber()));
            return memberToDTO(saved);
        }
    }

    // ─── Login — returns JWT token ────────────────────────────────────────────
    public AuthResponse login(LoginRequest request) {
        // Check Admin first
        Optional<Admin> admin = adminRepository
                .findByEmailAndPhoneNumber(request.getEmail(), request.getPhoneNumber());
        if (admin.isPresent()) {
            Admin a = admin.get();
            String token = jwtUtils.generateToken(a.getEmail(), "ADMIN");
            return new AuthResponse(token, "Bearer", a.getId(), a.getName(), a.getEmail(), "ADMIN");
        }

        // Check Member
        Optional<Member> member = memberRepository
                .findByEmailAndPhoneNumber(request.getEmail(), request.getPhoneNumber());
        if (member.isPresent()) {
            Member m = member.get();
            String token = jwtUtils.generateToken(m.getEmail(), "MEMBER");
            return new AuthResponse(token, "Bearer", m.getId(), m.getName(), m.getEmail(), "MEMBER");
        }

        throw new ResourceNotFoundException("Invalid email or phone number.");
    }

    // ─── View all users ───────────────────────────────────────────────────────
    public List<UserDTO> getAllUsers() {
        List<UserDTO> all = new ArrayList<>();
        adminRepository.findAll().stream().map(this::adminToDTO).forEach(all::add);
        memberRepository.findAll().stream().map(this::memberToDTO).forEach(all::add);
        return all;
    }

    public List<UserDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::memberToDTO).collect(Collectors.toList());
    }

    public Member getMemberEntityById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + id));
    }

    public void deleteAllData() {
        memberRepository.deleteAll();
        adminRepository.deleteAll();
    }

    // ─── Mappers ──────────────────────────────────────────────────────────────
    private UserDTO adminToDTO(Admin a) {
        return new UserDTO(a.getId(), a.getName(), a.getEmail(), a.getPhoneNumber(), "ADMIN");
    }

    private UserDTO memberToDTO(Member m) {
        return new UserDTO(m.getId(), m.getName(), m.getEmail(), m.getPhoneNumber(), "MEMBER");
    }
}