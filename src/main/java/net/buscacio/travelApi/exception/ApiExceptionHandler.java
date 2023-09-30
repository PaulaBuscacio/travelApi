package net.buscacio.travelApi.exception;

import jakarta.persistence.NonUniqueResultException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.io.IOException;


@ControllerAdvice
public class ApiExceptionHandler  {

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity<MessageException> handleException(ResourceException ex) {
       MessageException msg = new MessageException(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);

    }

    @ExceptionHandler({IOException.class, NullPointerException.class, StringIndexOutOfBoundsException.class})
    public ResponseEntity<MessageException> handleException(Exception e) {
        MessageException msg = new MessageException("upload file error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, MultipartException.class, MissingServletRequestPartException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<MessageException> handleException(Exception e, HttpServletRequest request) {
        MessageException ex = new MessageException(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
    }

    @ExceptionHandler(NonUniqueResultException.class)
    public ResponseEntity<MessageException> handleException(NonUniqueResultException e, HttpServletRequest request) {
        MessageException ex = new MessageException("resource already exists");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
    }

}
