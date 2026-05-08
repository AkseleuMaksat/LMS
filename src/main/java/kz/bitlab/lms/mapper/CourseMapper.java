package kz.bitlab.lms.mapper;

import kz.bitlab.lms.dto.CourseCreateRequest;
import kz.bitlab.lms.dto.CourseResponse;
import kz.bitlab.lms.dto.CourseUpdateRequest;
import kz.bitlab.lms.model.Course;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {ChapterMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CourseMapper {

    CourseResponse toDto(Course course);

    @Mapping(target = "chapters", ignore = true)
    Course toEntity(CourseCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "chapters", ignore = true)
    void updateEntityFromDto(CourseUpdateRequest request, @MappingTarget Course entity);

    List<CourseResponse> toDtoList(List<Course> courses);
}