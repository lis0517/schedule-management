package com.lys.schedulemanagement.exception;

public class PasswordMismatchException extends RuntimeException{
    public PasswordMismatchException(String message){
        super(message);
    }
}
