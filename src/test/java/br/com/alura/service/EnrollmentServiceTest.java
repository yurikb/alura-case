package br.com.alura.service;

import br.com.alura.dto.EnrollmentDTO;
import br.com.alura.enums.CourseStatus;
import br.com.alura.exception.EnrollmentException;
import br.com.alura.model.Course;
import br.com.alura.model.Enrollment;
import br.com.alura.model.User;
import br.com.alura.repository.EnrollmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Test
    public void testEnrollUserInCourse() {
        User user = new User();
        Course course = new Course();
        course.setStatus(CourseStatus.ACTIVE);
        course.setCreatedAt(LocalDate.now());

        when(enrollmentRepository.existsByUserAndCourse(user, course)).thenReturn(false);
        when(enrollmentRepository.save(any(Enrollment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EnrollmentDTO enrollmentDTO = enrollmentService.enrollUserInCourse(user, course);

        assertNotNull(enrollmentDTO);
        assertEquals(user, enrollmentDTO.getUser());
        assertEquals(course, enrollmentDTO.getCourse());
        assertNotNull(enrollmentDTO.getEnrollmentDate());
    }

    @Test
    public void testEnrollUserInCourse_EnrollmentAlreadyExists() {
        User user = new User();
        Course course = new Course();
        course.setStatus(CourseStatus.ACTIVE);

        when(enrollmentRepository.existsByUserAndCourse(user, course)).thenReturn(true);

        assertThrows(EnrollmentException.class, () -> enrollmentService.enrollUserInCourse(user, course));
    }

    @Test
    public void testEnrollUserInCourse_InactiveCourse() {
        User user = new User();
        Course course = new Course();
        course.setStatus(CourseStatus.INACTIVE);

        assertThrows(EnrollmentException.class, () -> enrollmentService.enrollUserInCourse(user, course));
    }
}
