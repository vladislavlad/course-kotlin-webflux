package software.darkmatter.school.blog.domain.comment.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import software.darkmatter.school.blog.domain.post.data.Post;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostAndDeletedAtIsNull(Post post, Pageable pageable);
}
