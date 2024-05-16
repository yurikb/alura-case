package br.com.alura.controller;

import br.com.alura.dto.CourseNPSDTO;
import br.com.alura.dto.ReviewDTO;
import br.com.alura.model.Review;
import br.com.alura.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/addReview")
    public ResponseEntity<Review> addReview(@RequestBody ReviewDTO reviewDTO) {
        Review review = reviewService.addReview(reviewDTO);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/nps/{courseId}")
    public ResponseEntity<Double> getNPS(@PathVariable UUID courseId) {
        double nps = reviewService.calculateNPS(courseId);
        return ResponseEntity.ok(nps);
    }

    @GetMapping("/courses/nps")
    public ResponseEntity<List<CourseNPSDTO>> getCoursesWithNPS() {
        List<CourseNPSDTO> coursesWithNPS = reviewService.getCoursesWithNPS();
        return ResponseEntity.ok(coursesWithNPS);
    }

}
