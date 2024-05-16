package br.com.alura.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @NotNull(message = "User cannot be null")
    @NotBlank(message = "User cannot be blank")
    private User user;

    @ManyToOne
    @NotNull(message = "Course cannot be null")
    @NotBlank(message = "Course cannot be blank")
    private Course course;

    @NotNull(message = "Enrollment date cannot be null")
    @NotBlank(message = "Enrollment date cannot be blank")
    private LocalDate enrollmentDate;

}
