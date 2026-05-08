package kz.bitlab.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.bitlab.lms.dto.LessonCreateRequest;
import kz.bitlab.lms.dto.LessonResponse;
import kz.bitlab.lms.dto.LessonUpdateRequest;
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

    @PostMapping
    @Operation(summary = "Create a new lesson")
    public ResponseEntity<LessonResponse> create(@Valid @RequestBody LessonCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(lessonService.createLesson(request));
    }

    @GetMapping("/by-chapter/{chapterId}")
    @Operation(summary = "Get all lessons for a chapter")
    public ResponseEntity<List<LessonResponse>> getLessonsByChapter(@PathVariable Long chapterId) {
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.getLessonsByChapterId(chapterId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get lesson by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lesson found"),
            @ApiResponse(responseCode = "404", description = "Lesson not found")
    })
    public ResponseEntity<LessonResponse> getLessonById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.getLessonById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing lesson")
    public LessonResponse update(@PathVariable Long id,
                                 @Valid @RequestBody LessonUpdateRequest req) {
        return lessonService.updateLesson(id, req);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a lesson by ID")
    public void delete(@PathVariable Long id) {
        lessonService.deleteLesson(id);
    }
}