package com.lys.schedulemanagement.comment.exception;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(String message){
        super(message);
    }
}
