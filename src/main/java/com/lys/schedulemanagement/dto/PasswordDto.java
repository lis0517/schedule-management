package com.lys.schedulemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordDto {
    @NotBlank(message = "제목 작성 필요")
    private String password;
}
