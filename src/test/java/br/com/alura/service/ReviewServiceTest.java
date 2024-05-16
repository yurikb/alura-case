package br.com.alura.service;

import br.com.alura.dto.ReviewDTO;
import br.com.alura.enums.CourseStatus;
import br.com.alura.exception.BadRequestException;
import br.com.alura.model.Course;
import br.com.alura.model.Review;
import br.com.alura.model.User;
import br.com.alura.repository.CourseRepository;
import br.com.alura.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    public void testAddReview() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setRating(9);
        reviewDTO.setFeedback("Great course!");

        Review review = new Review();
        review.setId(UUID.randomUUID());
        review.setRating(reviewDTO.getRating());
        review.setFeedback(reviewDTO.getFeedback());

        Course course = new Course();
        User instructor = new User();
        course.setStatus(CourseStatus.ACTIVE);
        course.setInstructor(instructor);

        when(modelMapper.map(reviewDTO, Review.class)).thenReturn(review);
        when(courseRepository.findById(review.getId())).thenReturn(Optional.of(course));
        when(reviewRepository.save(review)).thenReturn(review);

        Review result = reviewService.addReview(reviewDTO);

        assertNotNull(review);
        assertEquals(9, result.getRating());
        assertEquals("Great course!", result.getFeedback());
        verify(modelMapper, times(1)).map(reviewDTO, Review.class);
        verify(courseRepository, times(1)).findById(review.getId());
    }

    @Test
    public void testAddReviewException() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setRating(9);
        reviewDTO.setFeedback("Great course!");

        Review review = new Review();
        review.setId(UUID.randomUUID());

        Course course = new Course();
        course.setStatus(CourseStatus.INACTIVE);

        when(modelMapper.map(reviewDTO, Review.class)).thenReturn(review);
        when(courseRepository.findById(review.getId())).thenReturn(Optional.of(course));

        assertThrows(BadRequestException.class, () -> reviewService.addReview(reviewDTO));
    }
}
