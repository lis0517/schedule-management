package com.lys.schedulemanagement.user.exception;

public class PasswordMismatchException extends RuntimeException{
    public PasswordMismatchException(String message){
        super(message);
    }
}
