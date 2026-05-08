package kz.bitlab.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ChapterResponse(
        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "Chapter name", example = "Control Flow Statements")
        String name,

        @Schema(description = "Chapter description")
        String description,

        @Schema(description = "Order within the course", example = "2")
        int order,

        @Schema(description = "Parent course ID", example = "1")
        Long courseId,

        @Schema(description = "List of lessons in the chapter")
        List<LessonResponse> lessons
) {
}
