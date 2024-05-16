package com.lys.schedulemanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    @NotBlank(message = "제목 작성 필요")
    @Size(max = 200, message = "제목은 200자 제한이 있습니다.")
    private String title;

    private String content;
    @NotBlank(message = "담당자 작성 필요")
    @Email(message = "담당자는 이메일 형식을 가져야합니다.")
    private String author;
    @NotBlank(message = "패스워드 작성 필요")
    private String password;
}
