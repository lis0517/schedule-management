package com.lys.schedulemanagement.exception;

public class ScheduleAreadyDeletedException extends RuntimeException{
    public ScheduleAreadyDeletedException(String message){
        super(message);
    }
}
