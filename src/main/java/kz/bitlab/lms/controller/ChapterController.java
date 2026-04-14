package kz.bitlab.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.bitlab.lms.dto.ChapterDto;
import kz.bitlab.lms.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chapters")
@RequiredArgsConstructor
@Tag(name = "Chapter API", description = "Endpoints for managing chapters")
public class ChapterController {

    private final ChapterService chapterService;

    @GetMapping("/by-course/{courseId}")
    @Operation(summary = "Get all chapters for a course")
    public ResponseEntity<List<ChapterDto>> getChaptersByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(chapterService.getChaptersByCourseId(courseId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get chapter by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Chapter found"),
            @ApiResponse(responseCode = "404", description = "Chapter not found")
    })
    public ResponseEntity<ChapterDto> getChapterById(@PathVariable Long id) {
        return ResponseEntity.ok(chapterService.getChapterById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new chapter")
    public ResponseEntity<ChapterDto> createChapter(@Valid @RequestBody ChapterDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(chapterService.createChapter(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing chapter")
    public ResponseEntity<ChapterDto> updateChapter(
            @PathVariable Long id,
            @Valid @RequestBody ChapterDto dto) {
        return ResponseEntity.ok(chapterService.updateChapter(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a chapter by ID")
    public ResponseEntity<Void> deleteChapter(@PathVariable Long id) {
        chapterService.deleteChapter(id);
        return ResponseEntity.noContent().build();
    }
}