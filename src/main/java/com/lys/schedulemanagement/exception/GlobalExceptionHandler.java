package com.lys.schedulemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<String> handlePasswordMismatchException(PasswordMismatchException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<String> handleScheduleNotFoundException(ScheduleNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ScheduleAreadyDeletedException.class)
    public ResponseEntity<String> handleScheduleAreadyDeletedException(ScheduleAreadyDeletedException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(FileTypeMismatchException.class)
    public ResponseEntity<String> handleFileTypeMismatchException(FileTypeMismatchException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(DownloadFileNotFoundException.class)
    public ResponseEntity<String> handleDownloadFileNotFoundException(DownloadFileNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // 유효성 검사 실패 시 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodsArgumentNotValidException(MethodArgumentNotValidException ex){
        StringBuilder errorMsg = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errorMsg.append(error.getDefaultMessage()).append(", ");
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg.toString());
    }
}
