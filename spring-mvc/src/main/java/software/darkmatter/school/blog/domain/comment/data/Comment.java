package software.darkmatter.school.blog.domain.comment.data;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import software.darkmatter.school.blog.domain.post.data.Post;
import software.darkmatter.school.blog.domain.user.data.User;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private String text;

    private OffsetDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private User createdBy;

    private OffsetDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "updated_by_user_id")
    private User updatedBy;

    private OffsetDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "deleted_by_user_id")
    private User deletedBy;
}
