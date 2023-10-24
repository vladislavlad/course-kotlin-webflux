package software.darkmatter.school.blog.domain.post.business;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class PostEnriched {

    private Long id;

    private String title;

    private String summary;

    private String content;

    private OffsetDateTime createdAt;

    private User createdBy;

    private OffsetDateTime updatedAt;

    private User updatedBy;

    private OffsetDateTime deletedAt;

    private User deletedBy;

    private OffsetDateTime publishedAt;

    class User {
        private Long id;
        private String username;
        private String firstName;
        private String lastName;
    }
}
