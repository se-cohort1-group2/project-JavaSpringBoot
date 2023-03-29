package sg.edu.ntu.m3project.m3project.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import sg.edu.ntu.m3project.m3project.helper.ResponseMessage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(Exception ex){
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ResponseMessage("User not found."));
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<?> handleTicketNotFoundException(Exception ex){
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ResponseMessage("Ticket not found."));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(Exception ex){
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ResponseMessage("Something went wrong. Please try again later."));
    }
}
