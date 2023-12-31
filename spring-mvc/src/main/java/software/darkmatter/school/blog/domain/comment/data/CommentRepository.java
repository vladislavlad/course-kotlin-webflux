package software.darkmatter.school.blog.domain.comment.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import software.darkmatter.school.blog.domain.post.data.Post;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndDeletedAtIsNull(Long id);

    List<Comment> findAllByPostAndDeletedAtIsNull(Post post, Pageable pageable);

    Optional<Comment> findByIdAndPostAndDeletedAtIsNull(Long id, Post post);
}
