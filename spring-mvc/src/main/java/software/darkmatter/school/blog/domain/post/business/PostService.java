package software.darkmatter.school.blog.domain.post.business;

import org.springframework.data.domain.Pageable;
import software.darkmatter.school.blog.api.dto.PostCreateDto;
import software.darkmatter.school.blog.domain.post.data.Post;

import java.util.List;

public interface PostService {

    List<Post> getList(Pageable pageable);

    Post getById(Long id);

    Post create(PostCreateDto postCreateDto);

    Post update(Long id, PostCreateDto postCreateDto);

    void delete(Long id);

    void publish(Long id);
}
