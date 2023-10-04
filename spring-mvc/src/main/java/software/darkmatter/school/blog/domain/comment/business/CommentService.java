package software.darkmatter.school.blog.domain.comment.business;

import org.springframework.data.domain.Pageable;
import software.darkmatter.school.blog.api.dto.CommentCreateDto;
import software.darkmatter.school.blog.api.dto.CommentUpdateDto;
import software.darkmatter.school.blog.domain.comment.data.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getListByPostId(Long postId, Pageable pageable);

    Comment getById(Long id);

    Comment getByPostIdAndId(Long postId, Long id);

    Comment create(Long postId, CommentCreateDto createDto);

    Comment update(Long id, CommentUpdateDto updateDto);

    void delete(Long id);
}
