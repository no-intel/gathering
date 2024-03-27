package org.noint.gathering.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginReqDto(
        @NotBlank(message = "이메일은 필수 값 입니다.")
        @Email(message = "이메일 형식으로 입력해 주세요.")
        String email,
        @NotBlank(message = "비밀번호는 필수 값 입니다.")
        String password
) {
}
