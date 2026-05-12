package kz.bitlab.lms.service.impl;

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
import kz.bitlab.lms.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final CourseRepository courseRepository;
    private final ChapterMapper chapterMapper;

    @Override
    public List<ChapterResponse> getChaptersByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new LmsException("Course not found with id: " + courseId, ExceptionStatus.COURSE_NOT_FOUND);
        }
        return chapterMapper.toDtoList(chapterRepository.findByCourseIdOrderByOrder(courseId));
    }

    @Override
    public ChapterResponse getChapterById(Long id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new LmsException("Chapter not found with id: " + id, ExceptionStatus.CHAPTER_NOT_FOUND));
        return chapterMapper.toDto(chapter);
    }

    @Override
    public ChapterResponse createChapter(ChapterCreateRequest request) {
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new LmsException("Course not found with id: " + request.courseId(), ExceptionStatus.COURSE_NOT_FOUND));
        Chapter chapter = chapterMapper.toEntity(request);
        chapter.setCourse(course);
        Chapter saved = chapterRepository.save(chapter);
        return chapterMapper.toDto(saved);
    }

    @Override
    public ChapterResponse updateChapter(Long id, ChapterUpdateRequest request) {
        Chapter existing = chapterRepository.findById(id)
                .orElseThrow(() -> new LmsException("Chapter not found with id: " + id, ExceptionStatus.CHAPTER_NOT_FOUND));
        chapterMapper.updateEntityFromDto(request, existing);
        if (request.courseId() != null) {
            Course course = courseRepository.findById(request.courseId())
                    .orElseThrow(() -> new LmsException("Course not found with id: " + request.courseId(), ExceptionStatus.COURSE_NOT_FOUND));
            existing.setCourse(course);
        }
        Chapter updated = chapterRepository.save(existing);
        return chapterMapper.toDto(updated);
    }

    @Override
    public void deleteChapter(Long id) {
        Chapter existing = chapterRepository.findById(id)
                .orElseThrow(() -> new LmsException("Chapter not found with id: " + id, ExceptionStatus.CHAPTER_NOT_FOUND));
        chapterRepository.delete(existing);
    }
}