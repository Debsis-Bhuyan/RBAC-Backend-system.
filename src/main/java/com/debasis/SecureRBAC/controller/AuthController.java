package com.debasis.SecureRBAC.controller;

import com.debasis.SecureRBAC.dto.JwtResponse;
import com.debasis.SecureRBAC.dto.LoginRequest;
import com.debasis.SecureRBAC.dto.RegisterRequest;
import com.debasis.SecureRBAC.model.Role;
import com.debasis.SecureRBAC.model.User;
import com.debasis.SecureRBAC.repository.RoleRepository;
import com.debasis.SecureRBAC.repository.UserRepository;
import com.debasis.SecureRBAC.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            String jwt = jwtUtils.generateToken(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles));
        }
        catch (Exception ex){
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser( @RequestBody RegisterRequest registerRequest) {
        try{
            if (userRepository.existsByUsername(registerRequest.getUsername())) {
                return ResponseEntity.badRequest().body("Error: Username is already taken!");
            }
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            if (registerRequest.getRoles() == null || registerRequest.getRoles().isEmpty()) {
                Role userRole = roleRepository.findByName("USER");
                user.getRoles().add(userRole);
            } else {
                for (String roleName : registerRequest.getRoles()) {
                    Role role = roleRepository.findByName(roleName);
                    user.getRoles().add(role);
                }
            }
            userRepository.save(user);
            return ResponseEntity.ok("User registered successfully!");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Unable to register user! "+e.getMessage());
        }
    }
    @PostMapping("/admin/register")
    public ResponseEntity<?> adminRegisterUser( @RequestBody RegisterRequest registerRequest) {
        try{
            if (userRepository.existsByUsername(registerRequest.getUsername())) {
                return ResponseEntity.badRequest().body("Error: Username is already Exist!");
            }
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            if (registerRequest.getRoles() == null || registerRequest.getRoles().isEmpty()) {
                Role userRole = roleRepository.findByName("MODERATOR");
                user.getRoles().add(userRole);
            } else {
                for (String roleName : registerRequest.getRoles()) {
                    Role role = roleRepository.findByName(roleName);
                    user.getRoles().add(role);
                }
            }
            userRepository.save(user);
            return ResponseEntity.ok("User registered successfully!");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Unable to register user! "+e.getMessage());
        }
    }
}
