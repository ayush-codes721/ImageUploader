package ImageUploader.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiError> handleError(Exception e) {

        return new ResponseEntity<>(ApiError.builder().error(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
