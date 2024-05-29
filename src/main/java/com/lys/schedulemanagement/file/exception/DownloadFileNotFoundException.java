package com.lys.schedulemanagement.file.exception;

public class DownloadFileNotFoundException extends RuntimeException{
    public DownloadFileNotFoundException(String message){
        super(message);
    }
}
