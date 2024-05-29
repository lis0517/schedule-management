package com.lys.schedulemanagement.comment.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String content;
    private String author;
    private Long scheduleId;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = comment.getAuthor();
        this.scheduleId = comment.getSchedule().getId();
        this.createdAt = comment.getCreatedAt();
    }

}
