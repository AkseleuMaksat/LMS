package kz.bitlab.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LessonCreateRequest(
        @NotBlank(message = "Lesson name must not be blank")
        @Size(max = 255, message = "Lesson name must not exceed 255 characters")
        @Schema(description = "Lesson name", example = "Lecture: if-else Statements")
        String name,

        @Schema(description = "Short description of the lesson")
        String description,

        @Schema(description = "Full lesson content (HTML or plain text)")
        String content,

        @Min(value = 1, message = "Order must be at least 1")
        @Schema(description = "Order within the chapter", example = "1")
        int order,

        @NotNull(message = "Chapter ID must not be null")
        @Schema(description = "Parent chapter ID", example = "1")
        Long chapterId
) {
}
