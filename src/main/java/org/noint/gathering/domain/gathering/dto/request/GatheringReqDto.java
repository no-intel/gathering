package org.noint.gathering.domain.gathering.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record GatheringReqDto(
        @NotBlank(message = "모임 주제는 필수 입니다.")
        String subject,
        String description,
        @Min(value = 2, message = "최소 2인이상 설정 해야합니다.")
        @Max(value = 20, message = "최대 20인 까지 설정 가능합니다.")
        int maxMembers
) {
}
