package software.darkmatter.school.blog.domain.post.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import software.darkmatter.school.blog.domain.user.data.User;

import java.time.OffsetDateTime;

@Data
@Table("posts")
public class Post {
    @Id
    private Long id;

    private String title;

    private String summary;

    private String content;

    private OffsetDateTime createdAt;

    private Long createdByUserId;

    @Transient
    private User createdBy;

    private OffsetDateTime updatedAt;

    private Long updatedByUserId;

    private OffsetDateTime deletedAt;

    private Long deletedByUserId;

    private OffsetDateTime publishedAt;
}
