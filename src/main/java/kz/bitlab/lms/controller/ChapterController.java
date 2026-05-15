package kz.bitlab.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.bitlab.lms.dto.ChapterCreateRequest;
import kz.bitlab.lms.dto.ChapterResponse;
import kz.bitlab.lms.dto.ChapterUpdateRequest;
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

    @PostMapping
    @Operation(summary = "Create a new chapter")
    public ResponseEntity<ChapterResponse> create(@Valid @RequestBody ChapterCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(chapterService.createChapter(request));
    }

    @GetMapping("/by-course/{courseId}")
    @Operation(summary = "Get all chapters for a course")
    public ResponseEntity<List<ChapterResponse>> getChaptersByCourse(@PathVariable Long courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(chapterService.getChaptersByCourseId(courseId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get chapter by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Chapter found"),
            @ApiResponse(responseCode = "404", description = "Chapter not found")
    })
    public ResponseEntity<ChapterResponse> getChapterById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(chapterService.getChapterById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing chapter")
    public ResponseEntity<ChapterResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody ChapterUpdateRequest req) {
        ChapterResponse updated = chapterService.updateChapter(id, req);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a chapter by ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chapterService.deleteChapter(id);
        return ResponseEntity.noContent().build();
    }
}