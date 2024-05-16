package br.com.alura.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseNPSDTO {

    @NotNull
    @NotBlank
    private String courseName;

    @NotNull
    @NotBlank
    private String courseCode;

    private double nps;

}
