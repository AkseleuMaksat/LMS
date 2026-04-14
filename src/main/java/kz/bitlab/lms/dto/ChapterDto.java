package kz.bitlab.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Chapter data transfer object")
public class ChapterDto {

    @Schema(description = "Unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Chapter name must not be blank")
    @Size(max = 255, message = "Chapter name must not exceed 255 characters")
    @Schema(description = "Chapter name", example = "Control Flow Statements")
    private String name;

    @Schema(description = "Chapter description")
    private String description;

    @Min(value = 1, message = "Order must be at least 1")
    @Schema(description = "Order within the course", example = "2")
    private int order;

    @NotNull(message = "Course ID must not be null")
    @Schema(description = "Parent course ID", example = "1")
    private Long courseId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Schema(description = "List of lessons in the chapter")
    private List<LessonDto> lessons;
}