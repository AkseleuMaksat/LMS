package kz.bitlab.lms.mapper;

import kz.bitlab.lms.dto.AttachmentRs;
import kz.bitlab.lms.model.Attachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {
    @Mapping(source = "lesson.id", target = "lessonId")
    AttachmentRs toDto(Attachment attachment);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    Attachment toEntity(AttachmentRs dto);

    List<AttachmentRs> toDtos(List<Attachment> attachments);
}