package org.noint.gathering.domain.member.dto.response;

public record MyInfoResDto(
        Long memberId,
        String email,
        String name
) {
}
