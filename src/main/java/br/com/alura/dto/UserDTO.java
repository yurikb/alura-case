package br.com.alura.dto;

import br.com.alura.enums.Role;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Pattern(regexp = "^[a-z]+$", message = "Username must contain only lowercase letters and no spaces or numbers")
    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Email(message = "Email address is not valid")
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotNull(message = "Role cannot be null")
    @NotBlank(message = "Role cannot be blank")
    private Role role;

    @NotNull(message = "Created at cannot be null")
    @NotBlank(message = "Created at cannot be blank")
    private LocalDate createdAt;

    public UserDTO(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
