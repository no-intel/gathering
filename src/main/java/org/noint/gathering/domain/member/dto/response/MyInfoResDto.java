package org.noint.gathering.domain.member.dto.response;

import org.noint.gathering.entity.MemberStatus;

import java.math.BigDecimal;

public record MyInfoResDto(
        Long memberId,
        String email,
        String name,
        BigDecimal money

) {
}
