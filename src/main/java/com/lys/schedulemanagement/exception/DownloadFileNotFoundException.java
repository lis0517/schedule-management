package com.lys.schedulemanagement.exception;

public class DownloadFileNotFoundException extends RuntimeException{
    public DownloadFileNotFoundException(String message){
        super(message);
    }
}
