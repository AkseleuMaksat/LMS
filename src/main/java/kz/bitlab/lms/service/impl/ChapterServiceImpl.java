package kz.bitlab.lms.service.impl;

import kz.bitlab.lms.dto.ChapterDto;
import kz.bitlab.lms.entity.Chapter;
import kz.bitlab.lms.entity.Course;
import kz.bitlab.lms.exception.ResourceNotFoundException;
import kz.bitlab.lms.mapper.ChapterMapper;
import kz.bitlab.lms.repository.ChapterRepository;
import kz.bitlab.lms.repository.CourseRepository;
import kz.bitlab.lms.service.ChapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final CourseRepository courseRepository;
    private final ChapterMapper chapterMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ChapterDto> getChaptersByCourseId(Long courseId) {
        log.debug("Fetching chapters for course id: {}", courseId);
        verifyCourseExists(courseId);
        return chapterMapper.toDtoList(
                chapterRepository.findByCourseIdOrderByOrder(courseId));
    }

    @Override
    @Transactional(readOnly = true)
    public ChapterDto getChapterById(Long id) {
        log.debug("Fetching chapter by id: {}", id);
        return chapterMapper.toDto(findChapterOrThrow(id));
    }

    @Override
    @Transactional
    public ChapterDto createChapter(ChapterDto dto) {
        log.info("Creating chapter '{}' for course id: {}", dto.getName(), dto.getCourseId());
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + dto.getCourseId()));
        Chapter chapter = chapterMapper.toEntity(dto);
        chapter.setCourse(course);
        Chapter saved = chapterRepository.save(chapter);
        log.info("Chapter created successfully with id: {}", saved.getId());
        return chapterMapper.toDto(saved);
    }

    @Override
    @Transactional
    public ChapterDto updateChapter(Long id, ChapterDto dto) {
        log.info("Updating chapter with id: {}", id);
        Chapter existing = findChapterOrThrow(id);
        chapterMapper.updateEntityFromDto(dto, existing);
        if (dto.getCourseId() != null) {
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Course not found with id: " + dto.getCourseId()));
            existing.setCourse(course);
        }
        Chapter updated = chapterRepository.save(existing);
        log.info("Chapter updated successfully: id={}", id);
        return chapterMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteChapter(Long id) {
        log.info("Deleting chapter with id: {}", id);
        findChapterOrThrow(id);
        chapterRepository.deleteById(id);
        log.info("Chapter deleted successfully: id={}", id);
    }

    private Chapter findChapterOrThrow(Long id) {
        return chapterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Chapter not found with id: " + id));
    }

    private void verifyCourseExists(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found with id: " + courseId);
        }
    }
}