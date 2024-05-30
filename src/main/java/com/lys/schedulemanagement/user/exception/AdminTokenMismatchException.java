package com.lys.schedulemanagement.user.exception;

public class AdminTokenMismatchException extends RuntimeException {
    public AdminTokenMismatchException(String message){
        super(message);
    }
}
