package kz.bitlab.lms.service;

import kz.bitlab.lms.dto.ChapterCreateRequest;
import kz.bitlab.lms.dto.ChapterResponse;
import kz.bitlab.lms.dto.ChapterUpdateRequest;

import java.util.List;

public interface ChapterService {
    List<ChapterResponse> getChaptersByCourseId(Long courseId);

    ChapterResponse getChapterById(Long id);

    ChapterResponse createChapter(ChapterCreateRequest request);

    ChapterResponse updateChapter(Long id, ChapterUpdateRequest request);

    void deleteChapter(Long id);
}