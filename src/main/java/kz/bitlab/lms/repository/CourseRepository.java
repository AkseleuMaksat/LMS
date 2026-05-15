package kz.bitlab.lms.repository;

import kz.bitlab.lms.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @NonNull
    Page<Course> findAll(@NonNull Pageable pageable);

    @NonNull
    @EntityGraph(attributePaths = {"chapters"})
    Optional<Course> findById(@NonNull Long id);
}