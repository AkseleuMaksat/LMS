package kz.bitlab.lms.mapper;

import kz.bitlab.lms.dto.ChapterDto;
import kz.bitlab.lms.entity.Chapter;
import org.mapstruct.*;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {LessonMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ChapterMapper {

    @Mapping(source = "course.id", target = "courseId")
    ChapterDto toDto(Chapter chapter);

    @Mapping(source = "courseId", target = "course.id")
    @Mapping(target = "lessons", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Chapter toEntity(ChapterDto dto);

    List<ChapterDto> toDtoList(List<Chapter> chapters);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "courseId", target = "course.id")
    @Mapping(target = "lessons", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(ChapterDto dto, @MappingTarget Chapter entity);
}