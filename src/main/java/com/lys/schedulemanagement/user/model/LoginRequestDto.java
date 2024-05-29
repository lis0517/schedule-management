package com.lys.schedulemanagement.user.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {
    @NotBlank(message = "유저 이름은 공백이 될 수 없습니다.")
    private String username;
    @NotBlank(message = "패스워드는 공백이 될 수 없습니다.")
    private String password;
}
