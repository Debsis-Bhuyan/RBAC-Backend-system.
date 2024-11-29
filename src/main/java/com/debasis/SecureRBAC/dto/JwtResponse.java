package com.debasis.SecureRBAC.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtResponse {
    private String token;
//    private String type = "Bearer"; // The token type (e.g., Bearer token)
    private String username;
    private List<String> roles;
}
