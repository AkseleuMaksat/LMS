package kz.bitlab.lms.service.impl;

import kz.bitlab.lms.dto.LessonCreateRequest;
import kz.bitlab.lms.dto.LessonResponse;
import kz.bitlab.lms.dto.LessonUpdateRequest;
import kz.bitlab.lms.enums.ExceptionStatus;
import kz.bitlab.lms.exception.LmsException;
import kz.bitlab.lms.mapper.LessonMapper;
import kz.bitlab.lms.model.Chapter;
import kz.bitlab.lms.model.Lesson;
import kz.bitlab.lms.repository.ChapterRepository;
import kz.bitlab.lms.repository.LessonRepository;
import kz.bitlab.lms.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ChapterRepository chapterRepository;
    private final LessonMapper lessonMapper;

    @Override
    public List<LessonResponse> getLessonsByChapterId(Long chapterId) {
        if (!chapterRepository.existsById(chapterId)) {
            throw new LmsException("Chapter not found with id: " + chapterId, ExceptionStatus.CHAPTER_NOT_FOUND);
        }
        return lessonMapper.toDtoList(lessonRepository.findByChapterIdOrderByOrder(chapterId));
    }

    @Override
    public LessonResponse getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new LmsException("Lesson not found with id: " + id, ExceptionStatus.LESSON_NOT_FOUND));
        return lessonMapper.toDto(lesson);
    }

    @Override
    public LessonResponse createLesson(LessonCreateRequest request) {
        Chapter chapter = chapterRepository.findById(request.chapterId())
                .orElseThrow(() -> new LmsException("Chapter not found with id: " + request.chapterId(), ExceptionStatus.CHAPTER_NOT_FOUND));
        Lesson lesson = lessonMapper.toEntity(request);
        lesson.setChapter(chapter);
        Lesson saved = lessonRepository.save(lesson);
        return lessonMapper.toDto(saved);
    }

    @Override
    public LessonResponse updateLesson(Long id, LessonUpdateRequest request) {
        Lesson existing = lessonRepository.findById(id)
                .orElseThrow(() -> new LmsException("Lesson not found with id: " + id, ExceptionStatus.LESSON_NOT_FOUND));
        lessonMapper.updateEntityFromDto(request, existing);

        if (request.chapterId() != null) {
            Chapter chapter = chapterRepository.findById(request.chapterId())
                    .orElseThrow(() -> new LmsException("Chapter not found with id: " + request.chapterId(), ExceptionStatus.CHAPTER_NOT_FOUND));
            existing.setChapter(chapter);
        }
        Lesson updated = lessonRepository.save(existing);
        return lessonMapper.toDto(updated);
    }

    @Override
    public void deleteLesson(Long id) {
        Lesson existing = lessonRepository.findById(id)
                .orElseThrow(() -> new LmsException("Lesson not found with id: " + id, ExceptionStatus.LESSON_NOT_FOUND));
        lessonRepository.delete(existing);
    }
}