package kz.bitlab.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Lesson data transfer object")
public class LessonDto {

    @Schema(description = "Unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Lesson name must not be blank")
    @Size(max = 255, message = "Lesson name must not exceed 255 characters")
    @Schema(description = "Lesson name", example = "Lecture: if-else Statements")
    private String name;

    @Schema(description = "Short description of the lesson")
    private String description;

    @Schema(description = "Full lesson content (HTML or plain text)")
    private String content;

    @Min(value = 1, message = "Order must be at least 1")
    @Schema(description = "Order within the chapter", example = "1")
    private int order;

    @NotNull(message = "Chapter ID must not be null")
    @Schema(description = "Parent chapter ID", example = "1")
    private Long chapterId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
}