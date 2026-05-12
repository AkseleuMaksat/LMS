package kz.bitlab.lms.dto.user;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record RoleAssignRequest(
        @NotEmpty(message = "Roles list cannot be empty")
        List<String> roles
) {}
