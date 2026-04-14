package kz.bitlab.lms.service;

import kz.bitlab.lms.dto.CourseDto;
import kz.bitlab.lms.entity.Course;
import kz.bitlab.lms.exception.ResourceNotFoundException;
import kz.bitlab.lms.mapper.CourseMapper;
import kz.bitlab.lms.repository.CourseRepository;
import kz.bitlab.lms.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private CourseDto courseDto;

    @BeforeEach
    void setUp() {
        course = Course.builder()
                .id(1L)
                .name("Java Developer")
                .description("Comprehensive Java course")
                .build();

        courseDto = CourseDto.builder()
                .id(1L)
                .name("Java Developer")
                .description("Comprehensive Java course")
                .build();
    }

    @Test
    @DisplayName("getAllCourses - should return page of CourseDtos")
    void getAllCourses_ReturnsPageOfDtos() {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(0, 10);
        org.springframework.data.domain.Page<Course> page = new org.springframework.data.domain.PageImpl<>(List.of(course));

        when(courseRepository.findAll(pageable)).thenReturn(page);
        when(courseMapper.toDto(course)).thenReturn(courseDto);

        org.springframework.data.domain.Page<CourseDto> result = courseService.getAllCourses(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Java Developer");
        verify(courseRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("getCourseById - should return CourseDto when course exists")
    void getCourseById_WhenExists_ReturnsDto() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseMapper.toDto(course)).thenReturn(courseDto);

        CourseDto result = courseService.getCourseById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Java Developer");
    }

    @Test
    @DisplayName("getCourseById - should throw when course not found")
    void getCourseById_WhenNotFound_ThrowsException() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.getCourseById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("createCourse - should save and return CourseDto")
    void createCourse_SavesAndReturnsDto() {
        when(courseMapper.toEntity(courseDto)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.toDto(course)).thenReturn(courseDto);

        CourseDto result = courseService.createCourse(courseDto);

        assertThat(result.getName()).isEqualTo("Java Developer");
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    @DisplayName("deleteCourse - should delete when course exists")
    void deleteCourse_WhenExists_DeletesSuccessfully() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        courseService.deleteCourse(1L);

        verify(courseRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteCourse - should throw when course not found")
    void deleteCourse_WhenNotFound_ThrowsException() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.deleteCourse(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(courseRepository, never()).deleteById(any());
    }
}