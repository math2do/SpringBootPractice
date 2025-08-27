package in.math2do.practice.exception;

import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import in.math2do.practice.dtos.Common.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // Handle validation errors (e.g., @Valid request body)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex,
      HttpServletRequest request) {

    String errorMessage = ex.getBindingResult().getAllErrors().stream().findFirst()
        .map(err -> err.getDefaultMessage()).orElse("Validation failed");

    ErrorResponse error =
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), errorMessage);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  // Handle custom exceptions
  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFound(UsernameNotFoundException ex,
      HttpServletRequest request) {
    ErrorResponse error =
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  // Catch-all fallback handler
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex,
      HttpServletRequest request) {

    ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
        request.getRequestURI(), "Something went wrong!");

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
