package br.com.alura.repository;

import br.com.alura.model.Course;
import br.com.alura.model.Enrollment;
import br.com.alura.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    boolean existsByUserAndCourse(User user, Course course);
}
