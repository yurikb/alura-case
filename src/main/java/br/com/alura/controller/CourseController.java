package br.com.alura.controller;

import br.com.alura.dto.CourseDTO;
import br.com.alura.enums.CourseStatus;
import br.com.alura.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/inactivate/{code}")
    public CourseDTO inactivateCourse(@PathVariable String code) {
        return courseService.inactivateCourse(code);
    }

    @GetMapping
    public Page<CourseDTO> listCourses(@RequestParam(name = "status", required = false) CourseStatus status,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return courseService.listCoursesByStatus(status, page, size);
    }

    @PostMapping("/addCourse")
    public ResponseEntity<CourseDTO> addCourse(@RequestBody CourseDTO courseDTO) {
        CourseDTO course = courseService.addCourse(courseDTO);
        return ResponseEntity.ok(course);
    }
}
