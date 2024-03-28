package org.noint.gathering.domain.gathering.dto.response;

public record GatheringInfoResDto(
        String subject,
        String description,
        int maxMembers,
        String creatorName,
        Long creatorId
) {
}
