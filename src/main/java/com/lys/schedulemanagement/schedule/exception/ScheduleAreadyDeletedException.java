package com.lys.schedulemanagement.schedule.exception;

public class ScheduleAreadyDeletedException extends RuntimeException{
    public ScheduleAreadyDeletedException(String message){
        super(message);
    }
}
