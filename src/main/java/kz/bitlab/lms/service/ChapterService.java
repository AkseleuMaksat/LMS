package kz.bitlab.lms.service;

import kz.bitlab.lms.dto.ChapterDto;

import java.util.List;

public interface ChapterService {
    List<ChapterDto> getChaptersByCourseId(Long courseId);

    ChapterDto getChapterById(Long id);

    ChapterDto createChapter(ChapterDto dto);

    ChapterDto updateChapter(Long id, ChapterDto dto);

    void deleteChapter(Long id);
}