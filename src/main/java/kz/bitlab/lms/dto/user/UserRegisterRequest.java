package kz.bitlab.lms.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record UserRegisterRequest(
        @NotBlank(message = "Username cannot be empty")
        String username,
        
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email cannot be empty")
        String email,
        
        @NotBlank(message = "First name cannot be empty")
        String firstName,
        
        @NotBlank(message = "Last name cannot be empty")
        String lastName,
        
        @NotBlank(message = "Password cannot be empty")
        String password,
        
        List<String> roles
) {}
