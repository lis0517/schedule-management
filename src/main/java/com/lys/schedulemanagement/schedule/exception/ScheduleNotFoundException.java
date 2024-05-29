package com.lys.schedulemanagement.schedule.exception;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(String message){
        super(message);
    }
}
