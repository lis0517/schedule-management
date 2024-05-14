package com.lys.schedulemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestDto {
    private String title;
    private String content;
    private String author;
    private String password;
}
