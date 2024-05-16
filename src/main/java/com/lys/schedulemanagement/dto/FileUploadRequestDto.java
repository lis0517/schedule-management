package com.lys.schedulemanagement.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class FileUploadRequestDto {
    @NotBlank(message = "일정 아이디 필요")
    private Long scheduleId;
    @NotBlank(message = "파일이 비어있습니다.")
    private MultipartFile file;
}
