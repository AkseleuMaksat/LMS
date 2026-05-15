package kz.bitlab.lms.exception.core;

import kz.bitlab.lms.exception.LmsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerCustom {
    @ExceptionHandler(LmsException.class)
    public ResponseEntity<ErrorResponse> handleLms(LmsException exception) {
        HttpStatus status = resolveHttpStatus(exception.getStatus());
        ErrorResponse response = ErrorResponse.builder()
                .id(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now().toString())
                .status(status)
                .details(exception.getMessage())
                .build();
        return ResponseEntity.status(status).body(response);
    }

    private HttpStatus resolveHttpStatus(kz.bitlab.lms.exception.enums.ExceptionStatus status) {
        return switch (status) {
            case COURSE_NOT_FOUND, CHAPTER_NOT_FOUND, LESSON_NOT_FOUND, USER_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case FILE_UPLOAD_FAILED, FILE_DOWNLOAD_FAILED -> HttpStatus.INTERNAL_SERVER_ERROR;
            case USER_REGISTRATION_FAILED -> HttpStatus.BAD_REQUEST;
        };
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String details = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; "));
        ErrorResponse response = ErrorResponse.builder()
                .id(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.BAD_REQUEST)
                .details(details)
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        String details = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        ErrorResponse response = ErrorResponse.builder()
                .id(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.CONFLICT)
                .details("Database constraint violation: " + details)
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .id(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.FORBIDDEN)
                .details("Access denied: " + ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse response = ErrorResponse.builder()
                .id(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now().toString())
                .status(status)
                .details(exception.getMessage())
                .build();
        return ResponseEntity.status(status).body(response);
    }
}