package com.lys.schedulemanagement.exception;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(String message){
        super(message);
    }
}
