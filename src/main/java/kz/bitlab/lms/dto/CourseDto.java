package kz.bitlab.lms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Course data transfer object")
public class CourseDto {

    @Schema(description = "Unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Course name must not be blank")
    @Size(max = 255, message = "Course name must not exceed 255 characters")
    @Schema(description = "Course name", example = "Java Developer")
    private String name;

    @Schema(description = "Course description", example = "A comprehensive Java course")
    private String description;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Schema(description = "List of chapters in the course")
    private List<ChapterDto> chapters;
}