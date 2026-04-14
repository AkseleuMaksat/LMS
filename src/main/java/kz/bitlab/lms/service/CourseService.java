package kz.bitlab.lms.service;

import kz.bitlab.lms.dto.CourseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    Page<CourseDto> getAllCourses(Pageable pageable);

    CourseDto getCourseById(Long id);

    CourseDto createCourse(CourseDto dto);

    CourseDto updateCourse(Long id, CourseDto dto);

    void deleteCourse(Long id);
}