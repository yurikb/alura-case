package br.com.alura.controller;

import br.com.alura.dto.EnrollmentDTO;
import br.com.alura.model.Enrollment;
import br.com.alura.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enroll")
    public ResponseEntity<EnrollmentDTO> enrollUserInCourse(@RequestBody EnrollmentDTO enrollmentRequest) {
        EnrollmentDTO enrollment = enrollmentService.enrollUserInCourse(enrollmentRequest.getUser(), enrollmentRequest.getCourse());
        return new ResponseEntity<>(enrollment, HttpStatus.CREATED);
    }
}
