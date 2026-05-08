package kz.bitlab.lms.service;

import kz.bitlab.lms.dto.CourseCreateRequest;
import kz.bitlab.lms.dto.CourseResponse;
import kz.bitlab.lms.dto.CourseUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    Page<CourseResponse> getAllCourses(Pageable pageable);

    CourseResponse getCourseById(Long id);

    CourseResponse createCourse(CourseCreateRequest request);

    CourseResponse updateCourse(Long id, CourseUpdateRequest request);

    void deleteCourse(Long id);
}