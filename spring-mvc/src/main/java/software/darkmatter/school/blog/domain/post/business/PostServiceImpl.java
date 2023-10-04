package software.darkmatter.school.blog.domain.post.business;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.darkmatter.school.blog.api.dto.PostCreateDto;
import software.darkmatter.school.blog.domain.post.data.Post;
import software.darkmatter.school.blog.domain.post.data.PostRepository;
import software.darkmatter.school.blog.domain.post.error.PostNotFoundException;
import software.darkmatter.school.blog.domain.user.business.UserService;
import software.darkmatter.school.blog.domain.user.data.User;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final UserService userService;

    @Override
    public List<Post> getList(Pageable pageable) {
        return repository.findAll(pageable).stream()
                         .collect(Collectors.toList());
    }

    @Override
    public Post getById(Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    @Transactional
    public Post create(PostCreateDto postCreateDto) {
        User user = userService.getById(postCreateDto.userId());
        var post = new Post();
        post.setTitle(postCreateDto.title());
        post.setSummary(postCreateDto.summary());
        post.setContent(postCreateDto.content());
        post.setCreatedAt(OffsetDateTime.now());
        post.setCreatedBy(user);
        post.setUpdatedAt(OffsetDateTime.now());
        post.setUpdatedBy(user);
        return repository.save(post);
    }

    @Override
    @Transactional
    public Post update(Long id, PostCreateDto postCreateDto) {
        Post post = getById(id);
        post.setTitle(postCreateDto.title());
        post.setSummary(postCreateDto.summary());
        post.setContent(postCreateDto.content());
        return repository.save(post);
    }

    @Override
    @Transactional
    public void publish(Long id) {
        Post post = getById(id);
        post.setPublishedAt(OffsetDateTime.now());
        repository.save(post);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}