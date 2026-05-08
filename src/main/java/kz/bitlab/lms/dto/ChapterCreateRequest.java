package kz.bitlab.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChapterCreateRequest(
        @NotBlank(message = "Chapter name must not be blank")
        @Size(max = 255, message = "Chapter name must not exceed 255 characters")
        @Schema(description = "Chapter name", example = "Control Flow Statements")
        String name,

        @Schema(description = "Chapter description")
        String description,

        @Min(value = 1, message = "Order must be at least 1")
        @Schema(description = "Order within the course", example = "2")
        int order,

        @NotNull(message = "Course ID must not be null")
        @Schema(description = "Parent course ID", example = "1")
        Long courseId
) {
}
