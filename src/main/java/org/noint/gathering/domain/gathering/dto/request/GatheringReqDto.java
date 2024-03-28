package org.noint.gathering.domain.gathering.dto.request;

public record GatheringReqDto(
        String subject,
        String description,
        int max
) {
}
