package br.com.alura.service;

import br.com.alura.dto.UserDTO;
import br.com.alura.exception.BadRequestException;
import br.com.alura.exception.UserNotFoundException;
import br.com.alura.model.User;
import br.com.alura.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDTO addUser(UserDTO userDTO) {
        User existingRecord = userRepository.findByUsername(userDTO.getUsername());
        if (existingRecord != null) {
            throw new BadRequestException("This username is taken");
        }

        User user = modelMapper.map(userDTO, User.class);


        user.setCreatedAt(LocalDate.now());
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return modelMapper.map(user, UserDTO.class);
        }
        throw new UserNotFoundException("User '" + username + "' not found.");
    }

}
