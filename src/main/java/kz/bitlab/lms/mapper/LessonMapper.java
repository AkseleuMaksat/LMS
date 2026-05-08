package kz.bitlab.lms.mapper;

import kz.bitlab.lms.dto.LessonCreateRequest;
import kz.bitlab.lms.dto.LessonResponse;
import kz.bitlab.lms.dto.LessonUpdateRequest;
import kz.bitlab.lms.model.Lesson;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LessonMapper {

    @Mapping(source = "chapter.id", target = "chapterId")
    LessonResponse toDto(Lesson lesson);

    Lesson toEntity(LessonCreateRequest request);

    List<LessonResponse> toDtoList(List<Lesson> lessons);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(LessonUpdateRequest request, @MappingTarget Lesson entity);
}