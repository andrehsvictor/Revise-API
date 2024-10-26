package andrehsvictor.revise.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ReviseException extends RuntimeException {
    
    private final List<String> errors;
    private final HttpStatus status;

    public ReviseException(HttpStatus status, List<String> errors) {
        super();
        this.errors = errors;
        this.status = status;
    }
}
