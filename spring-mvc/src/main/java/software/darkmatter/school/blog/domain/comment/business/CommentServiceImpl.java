package software.darkmatter.school.blog.domain.comment.business;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.darkmatter.school.blog.api.dto.CommentCreateDto;
import software.darkmatter.school.blog.api.dto.CommentUpdateDto;
import software.darkmatter.school.blog.domain.comment.data.Comment;
import software.darkmatter.school.blog.domain.comment.data.CommentRepository;
import software.darkmatter.school.blog.domain.comment.error.CommentNotFoundException;
import software.darkmatter.school.blog.domain.post.business.PostService;
import software.darkmatter.school.blog.domain.post.data.Post;
import software.darkmatter.school.blog.domain.user.business.UserService;
import software.darkmatter.school.blog.domain.user.data.User;

import java.time.OffsetDateTime;
import java.util.List;

import static software.darkmatter.school.blog.config.security.SimpleAuthentication.simpleAuthFromContext;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final UserService userService;
    private final PostService postService;

    @Override
    public List<Comment> getListByPostId(Long postId, Pageable pageable) {
        Post post = postService.getById(postId);
        return repository.findAllByPostAndDeletedAtIsNull(post, pageable);
    }

    @Override
    public Comment getById(Long id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                         .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getByPostIdAndId(Long postId, Long id) {
        return repository.findByIdAndPostAndDeletedAtIsNull(id, postService.getById(postId))
                         .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Override
    @Transactional
    public Comment create(Long postId, CommentCreateDto createDto) {
        var authentication = simpleAuthFromContext();
        User user = userService.getById(authentication.getUserId());

        Post post = postService.getById(postId);
        var comment = new Comment();
        comment.setPost(post);
        comment.setText(createDto.text());
        comment.setCreatedAt(OffsetDateTime.now());
        comment.setCreatedBy(user);
        comment.setUpdatedAt(OffsetDateTime.now());
        comment.setUpdatedBy(user);
        return repository.save(comment);
    }

    @Override
    @Transactional
    public Comment update(Long id, CommentUpdateDto updateDto) {
        var authentication = simpleAuthFromContext();
        User user = userService.getById(authentication.getUserId());

        Comment comment = getById(id);
        comment.setText(updateDto.text());
        comment.setUpdatedAt(OffsetDateTime.now());
        comment.setUpdatedBy(user);
        return repository.save(comment);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var authentication = simpleAuthFromContext();
        User user = userService.getById(authentication.getUserId());

        Comment comment = getById(id);
        comment.setDeletedAt(OffsetDateTime.now());
        comment.setDeletedBy(user);
        repository.save(comment);
    }
}
