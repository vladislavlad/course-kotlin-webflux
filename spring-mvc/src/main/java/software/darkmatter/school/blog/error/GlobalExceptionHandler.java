package software.darkmatter.school.blog.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFound(HttpServletRequest req, NotFoundException nfe) {
        return new ResponseEntity<>(
            new ErrorDto(OffsetDateTime.now(), HttpStatus.NOT_FOUND.value(), nfe.getMessage(), req.getRequestURI()),
            HttpStatus.NOT_FOUND
        );
    }
}
