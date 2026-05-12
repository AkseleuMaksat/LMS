package kz.bitlab.lms.dto.user;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        String password
) {}
