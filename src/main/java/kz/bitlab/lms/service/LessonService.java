package kz.bitlab.lms.service;

import kz.bitlab.lms.dto.LessonDto;

import java.util.List;

public interface LessonService {
    List<LessonDto> getLessonsByChapterId(Long chapterId);

    LessonDto getLessonById(Long id);

    LessonDto createLesson(LessonDto dto);

    LessonDto updateLesson(Long id, LessonDto dto);

    void deleteLesson(Long id);
}