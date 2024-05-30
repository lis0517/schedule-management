package com.lys.schedulemanagement.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 알파벳 소문자와 숫자로 구성된 4자 이상 10자 이하야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 알파벳 대소문자와 숫자로 구성된 8자 이상 15자 이하야 합니다.")
    private String password;

    @NotBlank(message = "별명은 필수 입력 값입니다.")
    private String nickname;

    private boolean admin = false; // 관리자인지?
    private String adminToken = ""; // 관리자 키
}
