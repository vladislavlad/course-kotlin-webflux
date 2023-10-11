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

import static software.darkmatter.school.blog.config.security.SimpleAuthentication.simpleAuthFromContext;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final UserService userService;

    @Override
    public List<Post> getList(Pageable pageable) {
        return repository.findAllByDeletedAtIsNull(pageable);
    }

    @Override
    public Post getById(Long id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                         .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    @Transactional
    public Post create(PostCreateDto postCreateDto) {
        var authentication = simpleAuthFromContext();
        User user = userService.getById(authentication.getUserId());

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
        var authentication = simpleAuthFromContext();
        User user = userService.getById(authentication.getUserId());

        Post post = getById(id);
        post.setTitle(postCreateDto.title());
        post.setSummary(postCreateDto.summary());
        post.setContent(postCreateDto.content());
        post.setUpdatedAt(OffsetDateTime.now());
        post.setUpdatedBy(user);
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
    @Transactional
    public void delete(Long id) {
        var authentication = simpleAuthFromContext();
        User user = userService.getById(authentication.getUserId());

        Post post = getById(id);
        post.setDeletedAt(OffsetDateTime.now());
        post.setDeletedBy(user);
        repository.save(post);
    }
}
