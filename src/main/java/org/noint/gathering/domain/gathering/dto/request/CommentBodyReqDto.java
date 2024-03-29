package org.noint.gathering.domain.gathering.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CommentBodyReqDto(
        @NotEmpty(message = "메시지가 없습니다.")
        String body
) {
}
