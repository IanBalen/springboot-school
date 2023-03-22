package springframework.springschool.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


   @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Error<String>> BadExceptionHandler(RuntimeException ex){
       Error<String> error = new Error<>(ex.getMessage(), ZonedDateTime.now());
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error<Map<String, String>>> validationHandler(MethodArgumentNotValidException exception){
       Map<String, String> errorMap = new HashMap<>();
       exception.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
       Error<Map<String, String>> error = new Error<>(errorMap, ZonedDateTime.now());
       return ResponseEntity.badRequest().body(error);
   }


}
