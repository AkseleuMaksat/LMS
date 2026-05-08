package kz.bitlab.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record ChapterUpdateRequest(
        @Size(max = 255, message = "Chapter name must not exceed 255 characters")
        @Schema(description = "Chapter name", example = "Control Flow Statements")
        String name,

        @Schema(description = "Chapter description")
        String description,

        @Min(value = 1, message = "Order must be at least 1")
        @Schema(description = "Order within the course", example = "2")
        Integer order,

        @Schema(description = "Parent course ID", example = "1")
        Long courseId
) {
}
