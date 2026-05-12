package kz.bitlab.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.bitlab.lms.dto.user.RoleAssignRequest;
import kz.bitlab.lms.dto.user.UserRegisterRequest;
import kz.bitlab.lms.dto.user.UserUpdateRequest;
import kz.bitlab.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management API", description = "Endpoints for user registration, updates, and roles")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Register a new user (Admin only)")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/me")
    @Operation(summary = "Update own profile and password (Authenticated users)")
    public ResponseEntity<Void> updateMe(
            @Valid @RequestBody UserUpdateRequest request,
            Authentication authentication) {
        userService.updateUser(authentication.getName(), request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{username}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Assign roles to a user (Admin only)")
    public ResponseEntity<Void> assignRoles(
            @PathVariable String username,
            @Valid @RequestBody RoleAssignRequest request) {
        userService.assignRoles(username, request);
        return ResponseEntity.ok().build();
    }
}
