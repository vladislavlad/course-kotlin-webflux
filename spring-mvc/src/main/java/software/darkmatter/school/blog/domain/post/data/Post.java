package software.darkmatter.school.blog.domain.post.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import software.darkmatter.school.blog.domain.user.data.User;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String summary;

    private String content;

    private OffsetDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    private OffsetDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "updated_by_user_id")
    private User updatedBy;

    private OffsetDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "deleted_by_user_id")
    private User deletedBy;

    private OffsetDateTime publishedAt;
}
