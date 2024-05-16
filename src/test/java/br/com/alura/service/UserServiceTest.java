package br.com.alura.service;

import br.com.alura.dto.UserDTO;
import br.com.alura.enums.Role;
import br.com.alura.exception.UserNotFoundException;
import br.com.alura.model.User;
import br.com.alura.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void testAddUser() {
        UserDTO userDTO = new UserDTO("José", "José123", "jose.123@example.com", "password123", Role.STUDENT, LocalDate.now());
        User user = new User(UUID.randomUUID(), "José", "José123", "jose.123@example.com", "password123", Role.STUDENT, LocalDate.now());

        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO savedUserDTO = userService.addUser(userDTO);

        assertEquals(userDTO.getName(), savedUserDTO.getName());
        assertEquals(userDTO.getUsername(), savedUserDTO.getUsername());
        assertEquals(userDTO.getEmail(), savedUserDTO.getEmail());
        assertEquals(userDTO.getRole(), savedUserDTO.getRole());
        assertEquals(userDTO.getPassword(), savedUserDTO.getPassword());
        assertEquals(userDTO.getCreatedAt(), savedUserDTO.getCreatedAt());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetUserByUsername() {
        String username = "jose_silva";
        User user = new User(UUID.randomUUID(), "José Silva", username, "jose.silva@example.com", "password123", Role.STUDENT, LocalDate.now());
        UserDTO userDTO = new UserDTO("José Silva", username, "jose.silva@example.com", "password123", Role.STUDENT, LocalDate.now());

        when(userRepository.findByUsername(username)).thenReturn(user);
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO retrievedUserDTO = userService.getUserByUsername(username);

        assertEquals(user.getName(), retrievedUserDTO.getName());
        assertEquals(user.getEmail(), retrievedUserDTO.getEmail());
        assertEquals(user.getRole(), retrievedUserDTO.getRole());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testGetUserByUsername_UserNotFound() {
        String username = "non_existing_user";

        when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByUsername(username);
        });
        verify(userRepository, times(1)).findByUsername(username);
    }

}
