package com.lys.schedulemanagement.schedule.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;

    public ResponseDto(Schedule schedule) {
        id = schedule.getId();
        title = schedule.getTitle();
        content = schedule.getContent();
        author = schedule.getAuthor();
        createdAt = schedule.getCreatedAt();
    }
}
