package br.com.alura.service;

import br.com.alura.dto.EnrollmentDTO;
import br.com.alura.exception.EnrollmentException;
import br.com.alura.model.Course;
import br.com.alura.model.Enrollment;
import br.com.alura.model.User;
import br.com.alura.repository.EnrollmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Transactional
    public EnrollmentDTO enrollUserInCourse(User user, Course course) {
        // Cannot enroll twice in the same course
        if (enrollmentRepository.existsByUserAndCourse(user, course)) {
            throw new EnrollmentException("User already enrolled in this course");
        }

        // Cannot enroll into an inactive course
        if (!course.isActive()) {
            throw new EnrollmentException("Cannot enroll in an inactive course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());

        enrollmentRepository.save(enrollment);

        return modelMapper.map(enrollment, EnrollmentDTO.class);
    }
}
