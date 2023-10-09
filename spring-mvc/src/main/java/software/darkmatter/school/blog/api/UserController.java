package software.darkmatter.school.blog.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import software.darkmatter.school.blog.api.dto.UserCreateDto;
import software.darkmatter.school.blog.api.dto.UserDto;
import software.darkmatter.school.blog.domain.user.business.UserService;
import software.darkmatter.school.blog.domain.user.data.User;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return convertToDto(userService.getById(id));
    }

    @PostMapping
    public UserDto create(@RequestBody UserCreateDto userCreateDto) {
        return convertToDto(userService.create(userCreateDto));
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserCreateDto userCreateDto) {
        return convertToDto(userService.update(id, userCreateDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
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
