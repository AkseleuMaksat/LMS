package kz.bitlab.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record CourseResponse(
        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "Course name", example = "Java Developer")
        String name,

        @Schema(description = "Course description", example = "A comprehensive Java course")
        String description,

        @Schema(description = "List of chapters in the course")
        List<ChapterResponse> chapters
) {
}
