package software.darkmatter.school.blog.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDto {
    private OffsetDateTime timestamp;
    private int status;
    private String error;
    private String path;
}
