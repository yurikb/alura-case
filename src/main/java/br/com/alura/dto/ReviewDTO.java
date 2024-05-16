package br.com.alura.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewDTO {
    private UserDTO user;
    private CourseDTO course;
    private int rating;
    private String feedback;
    private LocalDate date;
}
