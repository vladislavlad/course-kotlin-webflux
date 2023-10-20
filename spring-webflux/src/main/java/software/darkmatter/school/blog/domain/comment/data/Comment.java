package software.darkmatter.school.blog.domain.comment.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

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

    private OffsetDateTime updatedAt;

    private Long updatedByUserId;

    private OffsetDateTime deletedAt;

    private Long deletedByUserId;
}
