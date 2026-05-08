package kz.bitlab.lms.service.impl;

import kz.bitlab.lms.dto.CourseCreateRequest;
import kz.bitlab.lms.dto.CourseResponse;
import kz.bitlab.lms.dto.CourseUpdateRequest;
import kz.bitlab.lms.enums.ExceptionStatus;
import kz.bitlab.lms.exception.LmsException;
import kz.bitlab.lms.mapper.CourseMapper;
import kz.bitlab.lms.model.Course;
import kz.bitlab.lms.repository.CourseRepository;
import kz.bitlab.lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public Page<CourseResponse> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable).map(courseMapper::toDto);
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new LmsException("Course not found with id: " + id, ExceptionStatus.COURSE_NOT_FOUND));
        return courseMapper.toDto(course);
    }

    @Override
    public CourseResponse createCourse(CourseCreateRequest request) {
        Course course = courseMapper.toEntity(request);
        Course saved = courseRepository.save(course);
        return courseMapper.toDto(saved);
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseUpdateRequest request) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new LmsException("Course not found with id: " + id, ExceptionStatus.COURSE_NOT_FOUND));
        courseMapper.updateEntityFromDto(request, existing);
        Course updated = courseRepository.save(existing);
        return courseMapper.toDto(updated);
    }

    @Override
    public void deleteCourse(Long id) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new LmsException("Course not found with id: " + id, ExceptionStatus.COURSE_NOT_FOUND));
        courseRepository.delete(existing);
    }
}