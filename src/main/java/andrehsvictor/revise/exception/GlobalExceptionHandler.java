package andrehsvictor.revise.exception;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDTO> handleAllExceptions(Exception ex) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .errors(List.of(ex.getMessage()))
                .build();
        return ResponseEntity.internalServerError().body(errorDTO);
    }

    @ExceptionHandler(ReviseException.class)
    public final ResponseEntity<ErrorDTO> handleReviseException(ReviseException ex) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .errors(ex.getErrors())
                .build();
        return ResponseEntity.status(ex.getStatus()).body(errorDTO);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        ErrorDTO errorDTO = ErrorDTO.builder()
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(errorDTO);
    }

}
