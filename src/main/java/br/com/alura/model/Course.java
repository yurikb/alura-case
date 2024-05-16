package br.com.alura.model;

import br.com.alura.enums.CourseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Column(length = 10, unique = true)
    @Pattern(regexp = "^[a-zA-Z-]+$", message = "Course code must be alphabetic characters and '-' only")
    @NotNull(message = "Code cannot be null")
    @NotBlank(message = "Code cannot be blank")
    private String code;

    private String description;

    @NotNull(message = "Status cannot be null. Either active or inactive")
    @NotBlank(message = "Status cannot be blank. Either active or inactive")
    private CourseStatus status;

    @NotNull(message = "createdAt cannot be null.")
    @NotBlank(message = "createdAt cannot be blank.")
    private LocalDate createdAt;

    @NotBlank(message = "inactivatedAt cannot be blank.")
    private LocalDate inactivatedAt;

    @ManyToOne
    private User instructor;

    @OneToMany(mappedBy = "course")
    private List<Review> reviews;

    public boolean isActive() {
        return status == CourseStatus.ACTIVE && inactivatedAt == null;
    }

}
