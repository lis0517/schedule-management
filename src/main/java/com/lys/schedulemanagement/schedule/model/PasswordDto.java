package com.lys.schedulemanagement.schedule.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {
    @NotBlank(message = "패스워드는 공백이 될 수 없습니다.")
    private String password;
}
