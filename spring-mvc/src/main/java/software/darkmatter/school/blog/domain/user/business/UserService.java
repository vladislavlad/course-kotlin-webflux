package software.darkmatter.school.blog.domain.user.business;

import org.springframework.data.domain.Pageable;
import software.darkmatter.school.blog.api.dto.UserCreateDto;
import software.darkmatter.school.blog.domain.user.data.User;

import java.util.List;

public interface UserService {

    List<User> getList(Pageable pageable);

    User getById(Long id);

    User create(UserCreateDto userCreateDto);

    User update(Long id, UserCreateDto userCreateDto);

    void delete(Long id);
}
