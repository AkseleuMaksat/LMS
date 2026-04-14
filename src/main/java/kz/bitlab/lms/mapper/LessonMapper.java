package kz.bitlab.lms.mapper;

import kz.bitlab.lms.dto.LessonDto;
import kz.bitlab.lms.entity.Lesson;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LessonMapper {

    @Mapping(source = "chapter.id", target = "chapterId")
    LessonDto toDto(Lesson lesson);

    @Mapping(source = "chapterId", target = "chapter.id")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Lesson toEntity(LessonDto dto);

    List<LessonDto> toDtoList(List<Lesson> lessons);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "chapterId", target = "chapter.id")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(LessonDto dto, @MappingTarget Lesson entity);
}