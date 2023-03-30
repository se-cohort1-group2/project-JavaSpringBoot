package sg.edu.ntu.m3project.m3project.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import sg.edu.ntu.m3project.m3project.helper.ResponseMessage;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<?> handleTicketNotFoundException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseMessage("Ticket not found."));
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<?> handleHttpMessageConversionException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMessage("Bad request."));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseMessage("Something went wrong. Please try again later."));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(ex.getMessage()));
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<?> handleNumberFormatException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseMessage("Something went wrong. Please try again later."));
    }
}
