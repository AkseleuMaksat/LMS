package kz.bitlab.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CourseCreateRequest(
        @NotBlank(message = "Course name must not be blank")
        @Size(max = 255, message = "Course name must not exceed 255 characters")
        @Schema(description = "Course name", example = "Java Developer")
        String name,

        @Schema(description = "Course description", example = "A comprehensive Java course")
        String description
) {
}
