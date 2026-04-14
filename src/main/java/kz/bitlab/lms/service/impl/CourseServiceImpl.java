package kz.bitlab.lms.service.impl;

import kz.bitlab.lms.dto.CourseDto;
import kz.bitlab.lms.entity.Course;
import kz.bitlab.lms.exception.ResourceNotFoundException;
import kz.bitlab.lms.mapper.CourseMapper;
import kz.bitlab.lms.repository.CourseRepository;
import kz.bitlab.lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CourseDto> getAllCourses(Pageable pageable) {
        log.debug("Fetching all courses with pagination");
        return courseRepository.findAll(pageable).map(courseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDto getCourseById(Long id) {
        log.debug("Fetching course by id: {}", id);
        Course course = findCourseOrThrow(id);
        return courseMapper.toDto(course);
    }

    @Override
    @Transactional
    public CourseDto createCourse(CourseDto dto) {
        log.info("Creating new course with name: {}", dto.getName());
        Course course = courseMapper.toEntity(dto);
        Course saved = courseRepository.save(course);
        log.info("Course created successfully with id: {}", saved.getId());
        return courseMapper.toDto(saved);
    }

    @Override
    @Transactional
    public CourseDto updateCourse(Long id, CourseDto dto) {
        log.info("Updating course with id: {}", id);
        Course existing = findCourseOrThrow(id);
        courseMapper.updateEntityFromDto(dto, existing);
        Course updated = courseRepository.save(existing);
        log.info("Course updated successfully: id={}", id);
        return courseMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        log.info("Deleting course with id: {}", id);
        findCourseOrThrow(id);
        courseRepository.deleteById(id);
        log.info("Course deleted successfully: id={}", id);
    }

    private Course findCourseOrThrow(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + id));
    }
}