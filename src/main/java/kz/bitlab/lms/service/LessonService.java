package kz.bitlab.lms.service;

import kz.bitlab.lms.dto.LessonCreateRequest;
import kz.bitlab.lms.dto.LessonResponse;
import kz.bitlab.lms.dto.LessonUpdateRequest;

import java.util.List;

public interface LessonService {
    List<LessonResponse> getLessonsByChapterId(Long chapterId);

    LessonResponse getLessonById(Long id);

    LessonResponse createLesson(LessonCreateRequest request);

    LessonResponse updateLesson(Long id, LessonUpdateRequest request);

    void deleteLesson(Long id);
}