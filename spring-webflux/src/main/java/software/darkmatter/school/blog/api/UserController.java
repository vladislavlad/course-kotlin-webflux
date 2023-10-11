package software.darkmatter.school.blog.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import software.darkmatter.school.blog.api.dto.UserCreateDto;
import software.darkmatter.school.blog.api.dto.UserDto;
import software.darkmatter.school.blog.domain.user.business.UserService;
import software.darkmatter.school.blog.domain.user.domain.User;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Mono<UserDto> getById(@PathVariable Long id) {
        return userService.getById(id)
                          .map(this::convertToDto);
    }

    @PostMapping
    public Mono<UserDto> create(@RequestBody UserCreateDto userCreateDto) {
        return userService.create(userCreateDto)
                          .map(this::convertToDto);
    }

    @PutMapping("/{id}")
    public Mono<UserDto> update(@PathVariable Long id, @RequestBody UserCreateDto userCreateDto) {
        return userService.update(id, userCreateDto)
                          .map(this::convertToDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    private UserDto convertToDto(User user) {
        return new UserDto(
            user.getId(),
            user.getUsername(),
            user.getFirstName(),
            user.getLastName()
        );
    }
}
