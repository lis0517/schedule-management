package com.lys.schedulemanagement.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileDownloadRequestDto {
    @NotBlank(message = "일정 아이디 필요")
    private Long scheduleId;
    @NotBlank(message = "파일 아이디 필요")
    private Long fileId;
}
