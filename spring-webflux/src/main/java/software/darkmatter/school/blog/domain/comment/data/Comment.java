package software.darkmatter.school.blog.domain.comment.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import software.darkmatter.school.blog.domain.user.data.User;

import java.time.OffsetDateTime;

@Data
@Table(name = "comments")
public class Comment {

    @Id
    private Long id;

    private Long postId;

    private String text;

    private OffsetDateTime createdAt;

    private Long createdByUserId;

    @Transient
    private User createdBy;

    private OffsetDateTime updatedAt;

    private Long updatedByUserId;

    private OffsetDateTime deletedAt;

    private Long deletedByUserId;
}
