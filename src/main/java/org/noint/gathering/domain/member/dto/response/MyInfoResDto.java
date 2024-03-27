package org.noint.gathering.domain.member.dto.response;

import java.math.BigDecimal;

public record MyInfoResDto(
        Long memberId,
        String email,
        String name,
        BigDecimal money

) {
}
