package kz.bitlab.lms.dto;

public record AttachmentRs(
        Long id,
        String name,
        String url,
        Long lessonId
) {}