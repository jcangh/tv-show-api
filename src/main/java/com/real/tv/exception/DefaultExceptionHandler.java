package com.real.tv.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

/**
 * Handler class to manage exceptions and REST status codes
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleException(EntityNotFoundException ex) {
        return new ResponseEntity<>(messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()), null,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UsernameNotFoundException ex) {
        return new ResponseEntity<>(messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()), null,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleUniqueKeyViolationException(DataIntegrityViolationException ex) {
        String message = messageSource.getMessage("msg.user-already-created", null,LocaleContextHolder.getLocale());
        return new ResponseEntity<>(message, null,
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MalFormedTokenException.class)
    public ResponseEntity<String> handleMalFormedException(MalFormedTokenException ex) {
        return new ResponseEntity<>(messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()), null,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<String> handleExpiredTokenException(ExpiredTokenException ex) {
        return new ResponseEntity<>(messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()), null,
                HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        return new ResponseEntity<>(messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()), null,
                HttpStatus.PRECONDITION_FAILED);
    }
}
