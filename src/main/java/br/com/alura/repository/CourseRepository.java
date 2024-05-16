package br.com.alura.repository;

import br.com.alura.enums.CourseStatus;
import br.com.alura.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    Course findByCode(String code);
    Page<Course> findByStatus(CourseStatus status, Pageable pageable);
}