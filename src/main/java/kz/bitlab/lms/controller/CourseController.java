package kz.bitlab.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.bitlab.lms.dto.CourseCreateRequest;
import kz.bitlab.lms.dto.CourseResponse;
import kz.bitlab.lms.dto.CourseUpdateRequest;
import kz.bitlab.lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = "Course API", description = "Endpoints for managing courses")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @Operation(summary = "Create a new course")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Course created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<CourseResponse> create(@Valid @RequestBody CourseCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.createCourse(request));
    }

    @GetMapping
    @Operation(summary = "Get all courses with pagination")
    @ApiResponse(responseCode = "200", description = "Page of courses")
    public ResponseEntity<Page<CourseResponse>> getAllCourses(@ParameterObject Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCourses(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Course found"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing course")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Course updated"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<CourseResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody CourseUpdateRequest req) {
        CourseResponse updated = courseService.updateCourse(id, req);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a course by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Course deleted"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}