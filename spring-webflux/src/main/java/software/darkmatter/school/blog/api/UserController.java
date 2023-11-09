package software.darkmatter.school.blog.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.darkmatter.school.blog.api.dto.UserCreateDto;
import software.darkmatter.school.blog.api.dto.UserDto;
import software.darkmatter.school.blog.domain.user.business.UserService;
import software.darkmatter.school.blog.domain.user.data.User;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public Mono<UserDto> getById(@PathVariable Long id) {
        return service.getById(id)
                      .map(this::convertToDto);
    }

    @GetMapping
    public Flux<UserDto> list(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size
    ) {
        return service.getList(Pageable.ofSize(size).withPage(page))
                      .map(this::convertToDto);
    }

    @PostMapping
    public Mono<UserDto> create(@Valid @RequestBody UserCreateDto userCreateDto) {
        return service.create(userCreateDto)
                      .map(this::convertToDto);
    }

    @PutMapping("/{id}")
    public Mono<UserDto> update(@PathVariable Long id, @Valid @RequestBody UserCreateDto userCreateDto) {
        return service.update(id, userCreateDto)
                      .map(this::convertToDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    private UserDto convertToDto(User user) {
        return new UserDto(
            user.getId(),
            user.getUuid(),
            user.getUsername(),
            user.getFirstName(),
            user.getLastName()
        );
    }
}
