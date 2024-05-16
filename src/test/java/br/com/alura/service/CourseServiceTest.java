package br.com.alura.service;

import br.com.alura.dto.CourseDTO;
import br.com.alura.enums.CourseStatus;
import br.com.alura.enums.Role;
import br.com.alura.exception.CourseNotFoundException;
import br.com.alura.model.Course;
import br.com.alura.model.User;
import br.com.alura.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CourseService courseService;

    @Test
    public void testInactivateCourse() {
        String code = "123";
        Course course = new Course();
        course.setCode(code);
        course.setName("Test Course");
        course.setStatus(CourseStatus.ACTIVE);

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setStatus(CourseStatus.INACTIVE);

        when(courseRepository.findByCode(code)).thenReturn(course);
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        when(modelMapper.map(course, CourseDTO.class)).thenReturn(courseDTO);

        CourseDTO result = courseService.inactivateCourse(code);

        assertNotNull(result);
        assertEquals(CourseStatus.INACTIVE, result.getStatus());
        verify(courseRepository, times(1)).findByCode(code);
        verify(courseRepository, times(1)).save(any(Course.class));
        verify(modelMapper, times(1)).map(course, CourseDTO.class);
    }

    @Test
    public void testInactivateCourseNotFound() {
        String code = "123";

        when(courseRepository.findByCode(code)).thenReturn(null);

        assertThrows(CourseNotFoundException.class, () -> courseService.inactivateCourse(code));
        verify(courseRepository, times(1)).findByCode(code);
        verify(courseRepository, times(0)).save(any(Course.class));
    }

    @Test
    public void testListCoursesByStatus() {
        Course course = new Course();
        course.setCode("123");
        course.setName("Test Course");
        course.setStatus(CourseStatus.ACTIVE);
        Page<Course> coursesPage = new PageImpl<>(Collections.singletonList(course));
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());

        when(courseRepository.findByStatus(CourseStatus.ACTIVE, pageable)).thenReturn(coursesPage);

        Page<CourseDTO> result = courseService.listCoursesByStatus(CourseStatus.ACTIVE, 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(courseRepository, times(1)).findByStatus(CourseStatus.ACTIVE, pageable);
    }

    @Test
    public void testListCoursesByStatusAll() {
        Course course = new Course();
        course.setCode("123");
        course.setName("Test Course");
        course.setStatus(CourseStatus.ACTIVE);
        Page<Course> coursesPage = new PageImpl<>(Collections.singletonList(course));
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());

        when(courseRepository.findAll(pageable)).thenReturn(coursesPage);

        Page<CourseDTO> result = courseService.listCoursesByStatus(null, 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(courseRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testAddCourse() {
        User instructor = new User(UUID.randomUUID(), "instructor", "username", "instructor@email.com", "password123", Role.INSTRUCTOR, LocalDate.now());
        CourseDTO courseDTO = new CourseDTO("Test Course", "123", "Test Description", CourseStatus.ACTIVE, LocalDate.now(), null, instructor, Collections.emptyList());
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setCode(courseDTO.getCode());
        course.setDescription(courseDTO.getDescription());
        course.setStatus(courseDTO.getStatus());
        course.setCreatedAt(courseDTO.getCreatedAt());
        course.setInstructor(courseDTO.getInstructor());
        course.setReviews(courseDTO.getReviews());

        when(modelMapper.map(courseDTO, Course.class)).thenReturn(course);
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        when(modelMapper.map(course, CourseDTO.class)).thenReturn(courseDTO);

        CourseDTO result = courseService.addCourse(courseDTO);

        assertNotNull(result);
        assertEquals(courseDTO.getName(), result.getName());
        verify(courseRepository, times(1)).save(any(Course.class));
    }
}
