package kz.bitlab.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LessonResponse(
        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "Lesson name", example = "Lecture: if-else Statements")
        String name,

        @Schema(description = "Short description of the lesson")
        String description,

        @Schema(description = "Full lesson content (HTML or plain text)")
        String content,

        @Schema(description = "Order within the chapter", example = "1")
        int order,

        @Schema(description = "Parent chapter ID", example = "1")
        Long chapterId
) {
}
