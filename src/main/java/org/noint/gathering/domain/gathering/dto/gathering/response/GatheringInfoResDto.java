package org.noint.gathering.domain.gathering.dto.gathering.response;

public record GatheringInfoResDto(
        String subject,
        String description,
        int maxMembers,
        String creatorName,
        Long creatorId
) {
}
