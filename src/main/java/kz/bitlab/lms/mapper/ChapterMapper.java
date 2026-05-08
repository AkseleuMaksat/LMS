package kz.bitlab.lms.mapper;

import kz.bitlab.lms.dto.ChapterCreateRequest;
import kz.bitlab.lms.dto.ChapterResponse;
import kz.bitlab.lms.dto.ChapterUpdateRequest;
import kz.bitlab.lms.model.Chapter;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {LessonMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ChapterMapper {

    @Mapping(source = "course.id", target = "courseId")
    ChapterResponse toDto(Chapter chapter);

    Chapter toEntity(ChapterCreateRequest request);

    List<ChapterResponse> toDtoList(List<Chapter> chapters);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "lessons", ignore = true)
    void updateEntityFromDto(ChapterUpdateRequest request, @MappingTarget Chapter entity);
}