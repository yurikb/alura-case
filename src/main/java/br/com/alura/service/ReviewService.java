package br.com.alura.service;

import br.com.alura.dto.CourseNPSDTO;
import br.com.alura.dto.ReviewDTO;
import br.com.alura.exception.BadRequestException;
import br.com.alura.exception.CourseNotFoundException;
import br.com.alura.exception.InsufficientReviewsException;
import br.com.alura.model.Course;
import br.com.alura.model.Review;
import br.com.alura.repository.CourseRepository;
import br.com.alura.repository.ReviewRepository;
import br.com.alura.util.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, CourseRepository courseRepository) {
        this.reviewRepository = reviewRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Review addReview(ReviewDTO reviewDTO) {
        Review review = modelMapper.map(reviewDTO, Review.class);

        Course course = courseRepository.findById(review.getId())
                .orElseThrow(() -> new CourseNotFoundException("Course with ID " + review.getId() + "not found"));

        if (!course.isActive()) {
            throw new BadRequestException("Cannot review an inactive course");
        }

        reviewRepository.save(review);

        if (review.getRating() < 6) {
            sendLowRatingNotification(course.getInstructor().getEmail(), review);
        }

        return review;
    }

    private void sendLowRatingNotification(String instructorEmail, Review review) {
        String subject = "Low Rating alert for your Course";
        String body = String.format("The course '%s' received a low rating.\n\nRating: %d\nFeedback: %s",
                review.getCourse().getName(), review.getRating(), review.getFeedback());

        EmailSender.send(instructorEmail, subject, body);
    }

    @Transactional(readOnly = true)
    public double calculateNPS(UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with ID " + courseId + "not found"));

        List<Review> reviews = reviewRepository.findByCourse(course);

        if (reviews.size() < 4) {
            throw new InsufficientReviewsException("Not enough reviews to calculate NPS");
        }

        long promoters = reviews.stream().filter(review -> review.getRating() >= 9).count();
        long detractors = reviews.stream().filter(review -> review.getRating() <= 6).count();
        long totalReviews = reviews.size();

        return ((double) (promoters - detractors) / totalReviews * 100);
    }

    @Transactional(readOnly = true)
    public List<CourseNPSDTO> getCoursesWithNPS() {
        List<Course> courses = courseRepository.findAll();

        if (courses.isEmpty()) {
            throw new BadRequestException("No courses found");
        }

        return courses.stream()
                .filter(course -> course.getReviews().size() >= 4)
                .map(course -> new CourseNPSDTO(
                        course.getName(), //
                        course.getCode(), //
                        calculateNPS(course.getId()) //
                ))
                .collect(Collectors.toList());
    }
}
