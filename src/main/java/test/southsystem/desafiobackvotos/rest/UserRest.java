package test.southsystem.desafiobackvotos.rest;

import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.southsystem.desafiobackvotos.model.dto.UserDTO;
import test.southsystem.desafiobackvotos.service.UserService;

@RestController
@RequestMapping("/users")
public class UserRest {

    private final UserService userService;

    public UserRest(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDTO post(@RequestBody @Valid final UserDTO user) {
        return userService.createUser(user);
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAllUser();
    }
}
