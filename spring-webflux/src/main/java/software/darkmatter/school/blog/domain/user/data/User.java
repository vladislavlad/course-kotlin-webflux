package software.darkmatter.school.blog.domain.user.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Data
@Table("users")
public class User {
    @Id
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private OffsetDateTime deletedAt;
}
