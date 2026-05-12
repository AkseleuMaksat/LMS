package kz.bitlab.lms.service;

import kz.bitlab.lms.dto.CourseCreateRequest;
import kz.bitlab.lms.dto.CourseResponse;
import kz.bitlab.lms.dto.CourseUpdateRequest;
import kz.bitlab.lms.exception.LmsException;
import kz.bitlab.lms.exception.enums.ExceptionStatus;
import kz.bitlab.lms.mapper.CourseMapper;
import kz.bitlab.lms.model.Course;
import kz.bitlab.lms.repository.CourseRepository;
import kz.bitlab.lms.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course;
    private CourseCreateRequest createRequest;
    private CourseResponse courseResponse;
    private CourseUpdateRequest updateRequest;

    @BeforeEach
    void setUp() {
        course = Course.builder()
                .name("Java Developer")
                .description("Comprehensive Java course")
                .build();
        course.setId(1L);

        createRequest = new CourseCreateRequest("Java Developer", "Comprehensive Java course");
        courseResponse = new CourseResponse(1L, "Java Developer", "Comprehensive Java course", java.util.Collections.emptyList());
        updateRequest = new CourseUpdateRequest("Java Dev Pro", "Updated course");
    }

    @Test
    @DisplayName("getAllCourses - should return page of CourseResponse")
    void getAllCourses_ReturnsPageOfResponses() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Course> page = new PageImpl<>(List.of(course));

        when(courseRepository.findAll(pageable)).thenReturn(page);
        when(courseMapper.toDto(course)).thenReturn(courseResponse);

        Page<CourseResponse> result = courseService.getAllCourses(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().name()).isEqualTo("Java Developer");
        verify(courseRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("getCourseById - should return CourseResponse when course exists")
    void getCourseById_WhenExists_ReturnsResponse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseMapper.toDto(course)).thenReturn(courseResponse);

        CourseResponse result = courseService.getCourseById(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Java Developer");
    }

    @Test
    @DisplayName("getCourseById - should throw when course not found")
    void getCourseById_WhenNotFound_ThrowsException() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.getCourseById(99L))
                .isInstanceOf(LmsException.class)
                .hasMessageContaining("99")
                .extracting("status")
                .isEqualTo(ExceptionStatus.COURSE_NOT_FOUND);
    }

    @Test
    @DisplayName("createCourse - should save and return CourseResponse")
    void createCourse_SavesAndReturnsResponse() {
        when(courseMapper.toEntity(createRequest)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.toDto(course)).thenReturn(courseResponse);

        CourseResponse result = courseService.createCourse(createRequest);

        assertThat(result.name()).isEqualTo("Java Developer");
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    @DisplayName("updateCourse - should update and return CourseResponse")
    void updateCourse_UpdatesAndReturnsResponse() {
        Course updatedCourse = Course.builder().name("Java Dev Pro").description("Updated course").build();
        updatedCourse.setId(1L);
        CourseResponse updatedResponse = new CourseResponse(1L, "Java Dev Pro", "Updated course", java.util.Collections.emptyList());

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        doNothing().when(courseMapper).updateEntityFromDto(updateRequest, course);
        when(courseRepository.save(course)).thenReturn(updatedCourse);
        when(courseMapper.toDto(updatedCourse)).thenReturn(updatedResponse);

        CourseResponse result = courseService.updateCourse(1L, updateRequest);

        assertThat(result.name()).isEqualTo("Java Dev Pro");
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    @DisplayName("deleteCourse - should delete when course exists")
    void deleteCourse_WhenExists_DeletesSuccessfully() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        courseService.deleteCourse(1L);

        verify(courseRepository, times(1)).delete(course);
    }

    @Test
    @DisplayName("deleteCourse - should throw when course not found")
    void deleteCourse_WhenNotFound_ThrowsException() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.deleteCourse(99L))
                .isInstanceOf(LmsException.class)
                .extracting("status")
                .isEqualTo(ExceptionStatus.COURSE_NOT_FOUND);

        verify(courseRepository, never()).delete(any());
    }
}