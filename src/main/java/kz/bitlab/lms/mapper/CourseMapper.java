package kz.bitlab.lms.mapper;

import kz.bitlab.lms.dto.CourseDto;
import kz.bitlab.lms.entity.Course;
import org.mapstruct.*;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {ChapterMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CourseMapper {

    CourseDto toDto(Course course);

    @Mapping(target = "chapters", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Course toEntity(CourseDto dto);

    List<CourseDto> toDtoList(List<Course> courses);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "chapters", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(CourseDto dto, @MappingTarget Course entity);
}