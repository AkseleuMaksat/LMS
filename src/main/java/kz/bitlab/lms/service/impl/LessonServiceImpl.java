// LessonServiceImpl.java
package kz.bitlab.lms.service.impl;

import kz.bitlab.lms.dto.LessonDto;
import kz.bitlab.lms.entity.Chapter;
import kz.bitlab.lms.entity.Lesson;
import kz.bitlab.lms.exception.ResourceNotFoundException;
import kz.bitlab.lms.mapper.LessonMapper;
import kz.bitlab.lms.repository.ChapterRepository;
import kz.bitlab.lms.repository.LessonRepository;
import kz.bitlab.lms.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ChapterRepository chapterRepository;
    private final LessonMapper lessonMapper;

    @Override
    @Transactional(readOnly = true)
    public List<LessonDto> getLessonsByChapterId(Long chapterId) {
        log.debug("Fetching lessons for chapter id: {}", chapterId);
        verifyChapterExists(chapterId);
        return lessonMapper.toDtoList(
                lessonRepository.findByChapterIdOrderByOrder(chapterId));
    }

    @Override
    @Transactional(readOnly = true)
    public LessonDto getLessonById(Long id) {
        log.debug("Fetching lesson by id: {}", id);
        return lessonMapper.toDto(findLessonOrThrow(id));
    }

    @Override
    @Transactional
    public LessonDto createLesson(LessonDto dto) {
        log.info("Creating lesson '{}' for chapter id: {}", dto.getName(), dto.getChapterId());
        Chapter chapter = chapterRepository.findById(dto.getChapterId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Chapter not found with id: " + dto.getChapterId()));
        Lesson lesson = lessonMapper.toEntity(dto);
        lesson.setChapter(chapter);
        Lesson saved = lessonRepository.save(lesson);
        log.info("Lesson created successfully with id: {}", saved.getId());
        return lessonMapper.toDto(saved);
    }

    @Override
    @Transactional
    public LessonDto updateLesson(Long id, LessonDto dto) {
        log.info("Updating lesson with id: {}", id);
        Lesson existing = findLessonOrThrow(id);
        lessonMapper.updateEntityFromDto(dto, existing);
        if (dto.getChapterId() != null) {
            Chapter chapter = chapterRepository.findById(dto.getChapterId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Chapter not found with id: " + dto.getChapterId()));
            existing.setChapter(chapter);
        }
        Lesson updated = lessonRepository.save(existing);
        log.info("Lesson updated successfully: id={}", id);
        return lessonMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteLesson(Long id) {
        log.info("Deleting lesson with id: {}", id);
        findLessonOrThrow(id);
        lessonRepository.deleteById(id);
        log.info("Lesson deleted successfully: id={}", id);
    }

    private Lesson findLessonOrThrow(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Lesson not found with id: " + id));
    }

    private void verifyChapterExists(Long chapterId) {
        if (!chapterRepository.existsById(chapterId)) {
            throw new ResourceNotFoundException("Chapter not found with id: " + chapterId);
        }
    }
}