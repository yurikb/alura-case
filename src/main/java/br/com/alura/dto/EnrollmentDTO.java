package br.com.alura.dto;

import br.com.alura.model.Course;
import br.com.alura.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnrollmentDTO {

    @NotNull(message = "User cannot be null")
    @NotBlank(message = "User cannot be blank")
    private User user;

    @NotNull(message = "Course cannot be null")
    @NotBlank(message = "Course cannot be blank")
    private Course course;

    @NotNull(message = "Enrollment date cannot be null")
    @NotBlank(message = "Enrollment date cannot be blank")
    private LocalDate enrollmentDate;

}
