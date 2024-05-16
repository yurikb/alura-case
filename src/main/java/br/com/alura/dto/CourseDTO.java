package br.com.alura.dto;

import br.com.alura.enums.CourseStatus;
import br.com.alura.model.Review;
import br.com.alura.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Pattern(regexp = "^[a-zA-Z-]+$", message = "Course code must be alphabetic characters and '-' only")
    @NotNull(message = "Code cannot be null")
    @NotBlank(message = "Code cannot be blank")
    @Size(max = 10, message = "Code max size is 10")
    private String code;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Status cannot be null. Either active or inactive")
    @NotBlank(message = "Status cannot be blank. Either active or inactive")
    private CourseStatus status;

    @NotNull(message = "CreatedAt cannot be null.")
    @NotBlank(message = "CreatedAt cannot be blank.")
    private LocalDate createdAt;

    @NotBlank(message = "InactivatedAt cannot be blank.")
    private LocalDate inactivatedAt;

    @NotBlank(message = "Instructor cannot be blank.")
    private User instructor;

    private List<Review> reviews;

    public CourseDTO(String name, String code, CourseStatus status) {
        this.name = name;
        this.code = code;
        this.status = status;
    }
}
