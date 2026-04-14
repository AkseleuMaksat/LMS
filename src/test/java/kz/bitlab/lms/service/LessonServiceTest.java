package kz.bitlab.lms.service;

import kz.bitlab.lms.dto.LessonDto;
import kz.bitlab.lms.entity.Chapter;
import kz.bitlab.lms.entity.Lesson;
import kz.bitlab.lms.exception.ResourceNotFoundException;
import kz.bitlab.lms.mapper.LessonMapper;
import kz.bitlab.lms.repository.ChapterRepository;
import kz.bitlab.lms.repository.LessonRepository;
import kz.bitlab.lms.service.impl.LessonServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock private LessonRepository lessonRepository;
    @Mock private ChapterRepository chapterRepository;
    @Mock private LessonMapper lessonMapper;

    @InjectMocks
    private LessonServiceImpl lessonService;

    private Chapter chapter;
    private Lesson lesson;
    private LessonDto lessonDto;

    @BeforeEach
    void setUp() {
        chapter = Chapter.builder().id(1L).name("Control Flow").build();

        lesson = Lesson.builder()
                .id(1L)
                .name("Lecture: if-else")
                .order(1)
                .chapter(chapter)
                .build();

        lessonDto = LessonDto.builder()
                .id(1L)
                .name("Lecture: if-else")
                .order(1)
                .chapterId(1L)
                .build();
    }

    @Test
    @DisplayName("getLessonsByChapterId - should return ordered lessons")
    void getLessonsByChapterId_ReturnsLessons() {
        when(chapterRepository.existsById(1L)).thenReturn(true);
        when(lessonRepository.findByChapterIdOrderByOrder(1L)).thenReturn(List.of(lesson));
        when(lessonMapper.toDtoList(List.of(lesson))).thenReturn(List.of(lessonDto));

        List<LessonDto> result = lessonService.getLessonsByChapterId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Lecture: if-else");
    }

    @Test
    @DisplayName("getLessonsByChapterId - should throw when chapter not found")
    void getLessonsByChapterId_ChapterNotFound_Throws() {
        when(chapterRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> lessonService.getLessonsByChapterId(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("createLesson - should create and return lesson")
    void createLesson_CreatesSuccessfully() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
        when(lessonMapper.toEntity(lessonDto)).thenReturn(lesson);
        when(lessonRepository.save(lesson)).thenReturn(lesson);
        when(lessonMapper.toDto(lesson)).thenReturn(lessonDto);

        LessonDto result = lessonService.createLesson(lessonDto);

        assertThat(result.getName()).isEqualTo("Lecture: if-else");
        verify(lessonRepository).save(lesson);
    }
}