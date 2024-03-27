package org.noint.gathering.domain.member.dto.request;

public record MemberJoinReqDto(
        String email,
        String name,
        String password
) {
}
