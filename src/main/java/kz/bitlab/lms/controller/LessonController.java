package kz.bitlab.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.bitlab.lms.dto.LessonDto;
import kz.bitlab.lms.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
@Tag(name = "Lesson API", description = "Endpoints for managing lessons")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/by-chapter/{chapterId}")
    @Operation(summary = "Get all lessons for a chapter")
    public ResponseEntity<List<LessonDto>> getLessonsByChapter(@PathVariable Long chapterId) {
        return ResponseEntity.ok(lessonService.getLessonsByChapterId(chapterId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get lesson by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lesson found"),
            @ApiResponse(responseCode = "404", description = "Lesson not found")
    })
    public ResponseEntity<LessonDto> getLessonById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new lesson")
    public ResponseEntity<LessonDto> createLesson(@Valid @RequestBody LessonDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(lessonService.createLesson(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing lesson")
    public ResponseEntity<LessonDto> updateLesson(
            @PathVariable Long id,
            @Valid @RequestBody LessonDto dto) {
        return ResponseEntity.ok(lessonService.updateLesson(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a lesson by ID")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }
}