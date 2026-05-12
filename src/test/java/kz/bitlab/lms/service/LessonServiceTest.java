package kz.bitlab.lms.service;

import kz.bitlab.lms.dto.LessonCreateRequest;
import kz.bitlab.lms.dto.LessonResponse;
import kz.bitlab.lms.exception.LmsException;
import kz.bitlab.lms.exception.enums.ExceptionStatus;
import kz.bitlab.lms.mapper.LessonMapper;
import kz.bitlab.lms.model.Chapter;
import kz.bitlab.lms.model.Lesson;
import kz.bitlab.lms.repository.ChapterRepository;
import kz.bitlab.lms.repository.LessonRepository;
import kz.bitlab.lms.service.impl.LessonServiceImpl;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private ChapterRepository chapterRepository;
    @Mock
    private LessonMapper lessonMapper;

    @InjectMocks
    private LessonServiceImpl lessonService;

    private Chapter chapter;
    private Lesson lesson;
    private LessonCreateRequest createRequest;
    private LessonResponse lessonResponse;

    @BeforeEach
    void setUp() {
        chapter = Chapter.builder().name("Control Flow").build();
        chapter.setId(1L);

        lesson = Lesson.builder()
                .name("Lecture: if-else")
                .description("Description")
                .content("Content")
                .order(1)
                .chapter(chapter)
                .build();
        lesson.setId(1L);

        createRequest = new LessonCreateRequest("Lecture: if-else", "Description", "Content", 1, 1L);
        lessonResponse = new LessonResponse(1L, "Lecture: if-else", "Description", "Content", 1, 1L);
    }

    @Test
    @DisplayName("getLessonsByChapterId - should return ordered lessons")
    void getLessonsByChapterId_ReturnsLessons() {
        when(chapterRepository.existsById(1L)).thenReturn(true);
        when(lessonRepository.findByChapterIdOrderByOrder(1L)).thenReturn(List.of(lesson));
        when(lessonMapper.toDtoList(List.of(lesson))).thenReturn(List.of(lessonResponse));

        List<LessonResponse> result = lessonService.getLessonsByChapterId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().name()).isEqualTo("Lecture: if-else");
    }

    @Test
    @DisplayName("getLessonsByChapterId - should throw when chapter not found")
    void getLessonsByChapterId_ChapterNotFound_Throws() {
        when(chapterRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> lessonService.getLessonsByChapterId(99L))
                .isInstanceOf(LmsException.class)
                .extracting("status")
                .isEqualTo(ExceptionStatus.CHAPTER_NOT_FOUND);
    }

    @Test
    @DisplayName("createLesson - should create and return lesson")
    void createLesson_CreatesSuccessfully() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
        when(lessonMapper.toEntity(createRequest)).thenReturn(lesson);
        when(lessonRepository.save(lesson)).thenReturn(lesson);
        when(lessonMapper.toDto(lesson)).thenReturn(lessonResponse);

        LessonResponse result = lessonService.createLesson(createRequest);

        assertThat(result.name()).isEqualTo("Lecture: if-else");
        verify(lessonRepository).save(lesson);
    }
}