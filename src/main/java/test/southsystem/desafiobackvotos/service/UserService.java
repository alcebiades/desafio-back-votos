package test.southsystem.desafiobackvotos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import test.southsystem.desafiobackvotos.model.dto.UserDTO;
import test.southsystem.desafiobackvotos.model.exception.NotFoundException;
import test.southsystem.desafiobackvotos.repository.UserRepository;
import test.southsystem.desafiobackvotos.repository.entity.User;

@Log4j2
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(final UserDTO userDTO) {

        final User user = userRepository.save(userDTO.toEntity());

        return user.toDTO();
    }

    public List<UserDTO> getAllUser() {

        final List<User> allAgenda = userRepository.findAll();

        return allAgenda.stream().map(User::toDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(final Long userId) {
        final Optional<User> user = userRepository.findById(userId);

        return user
                .map(User::toDTO)
                .orElseThrow(() -> new NotFoundException("Usuario nao encontrada com id: " + userId));
    }
}
