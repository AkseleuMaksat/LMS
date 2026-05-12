package kz.bitlab.lms.service;

import kz.bitlab.lms.dto.ChapterCreateRequest;
import kz.bitlab.lms.dto.ChapterResponse;
import kz.bitlab.lms.dto.ChapterUpdateRequest;
import kz.bitlab.lms.exception.enums.ExceptionStatus;
import kz.bitlab.lms.exception.LmsException;
import kz.bitlab.lms.mapper.ChapterMapper;
import kz.bitlab.lms.model.Chapter;
import kz.bitlab.lms.model.Course;
import kz.bitlab.lms.repository.ChapterRepository;
import kz.bitlab.lms.repository.CourseRepository;
import kz.bitlab.lms.service.impl.ChapterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChapterServiceTest {

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ChapterMapper chapterMapper;

    @InjectMocks
    private ChapterServiceImpl chapterService;

    private Course course;
    private Chapter chapter;
    private ChapterCreateRequest createRequest;
    private ChapterResponse chapterResponse;
    private ChapterUpdateRequest updateRequest;

    @BeforeEach
    void setUp() {
        course = Course.builder().name("Java").build();
        course.setId(1L);

        chapter = Chapter.builder()
                .name("Basics")
                .description("Java basics")
                .order(1)
                .course(course)
                .build();
        chapter.setId(1L);

        createRequest = new ChapterCreateRequest("Basics", "Java basics", 1, 1L);
        chapterResponse = new ChapterResponse(1L, "Basics", "Java basics", 1, 1L, Collections.emptyList());
        updateRequest = new ChapterUpdateRequest("Advanced", "Java advanced", 2, 1L);
    }

    @Test
    @DisplayName("getChaptersByCourseId - should return ordered chapters")
    void getChaptersByCourseId_ReturnsChapters() {
        when(courseRepository.existsById(1L)).thenReturn(true);
        when(chapterRepository.findByCourseIdOrderByOrder(1L)).thenReturn(List.of(chapter));
        when(chapterMapper.toDtoList(List.of(chapter))).thenReturn(List.of(chapterResponse));

        List<ChapterResponse> result = chapterService.getChaptersByCourseId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().name()).isEqualTo("Basics");
        verify(chapterRepository).findByCourseIdOrderByOrder(1L);
    }

    @Test
    @DisplayName("getChaptersByCourseId - should throw when course not found")
    void getChaptersByCourseId_CourseNotFound_Throws() {
        when(courseRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> chapterService.getChaptersByCourseId(99L))
                .isInstanceOf(LmsException.class)
                .extracting("status")
                .isEqualTo(ExceptionStatus.COURSE_NOT_FOUND);
    }

    @Test
    @DisplayName("getChapterById - should return chapter")
    void getChapterById_WhenExists_ReturnsChapter() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
        when(chapterMapper.toDto(chapter)).thenReturn(chapterResponse);

        ChapterResponse result = chapterService.getChapterById(1L);

        assertThat(result.name()).isEqualTo("Basics");
    }

    @Test
    @DisplayName("createChapter - should save and return chapter")
    void createChapter_SavesAndReturns() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(chapterMapper.toEntity(createRequest)).thenReturn(chapter);
        when(chapterRepository.save(chapter)).thenReturn(chapter);
        when(chapterMapper.toDto(chapter)).thenReturn(chapterResponse);

        ChapterResponse result = chapterService.createChapter(createRequest);

        assertThat(result.name()).isEqualTo("Basics");
        verify(chapterRepository).save(chapter);
    }

    @Test
    @DisplayName("updateChapter - should update and return chapter")
    void updateChapter_UpdatesAndReturns() {
        Chapter updatedChapter = Chapter.builder().name("Advanced").description("Java advanced").order(2).course(course).build();
        updatedChapter.setId(1L);
        ChapterResponse updatedResponse = new ChapterResponse(1L, "Advanced", "Java advanced", 2, 1L, Collections.emptyList());

        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
        doNothing().when(chapterMapper).updateEntityFromDto(updateRequest, chapter);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(chapterRepository.save(chapter)).thenReturn(updatedChapter);
        when(chapterMapper.toDto(updatedChapter)).thenReturn(updatedResponse);

        ChapterResponse result = chapterService.updateChapter(1L, updateRequest);

        assertThat(result.name()).isEqualTo("Advanced");
        verify(chapterRepository).save(chapter);
    }

    @Test
    @DisplayName("deleteChapter - should delete when chapter exists")
    void deleteChapter_WhenExists_DeletesSuccessfully() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));

        chapterService.deleteChapter(1L);

        verify(chapterRepository).delete(chapter);
    }
}
