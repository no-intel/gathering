package org.noint.gathering.domain.member.dto.request;

public record LoginReqDto(
        String email,
        String password
) {
}
