package br.com.alura.service;

import br.com.alura.dto.CourseDTO;
import br.com.alura.enums.CourseStatus;
import br.com.alura.exception.CourseNotFoundException;
import br.com.alura.model.Course;
import br.com.alura.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public CourseDTO inactivateCourse(String code) {
        Course course = courseRepository.findByCode(code);
        if (course != null) {
            course.setStatus(CourseStatus.INACTIVE);
            course.setInactivatedAt(LocalDate.now());
            courseRepository.save(course);
            return modelMapper.map(course, CourseDTO.class);
        }
        throw new CourseNotFoundException("Course with code '" + code + "' not found");
    }

    @Transactional(readOnly = true)
    public Page<CourseDTO> listCoursesByStatus(CourseStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Course> coursesPage;
        if (status != null && !String.valueOf(status).isBlank()) {
            coursesPage = courseRepository.findByStatus(status, pageable);
        } else {
            coursesPage = courseRepository.findAll(pageable);
        }
        return coursesPage.map(course -> modelMapper.map(course, CourseDTO.class));
    }

    @Transactional
    public CourseDTO addCourse(CourseDTO courseDTO) {
        Course course = modelMapper.map(courseDTO, Course.class);
        course.setCreatedAt(LocalDate.now());
        course.setInactivatedAt(null);
        courseRepository.save(course);
        return modelMapper.map(course, CourseDTO.class);
    }
}
