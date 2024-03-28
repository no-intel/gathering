package org.noint.gathering.domain.gathering.dto.response;

import com.querydsl.core.annotations.QueryProjection;

public record ParticipantsResDto(
        Long memberId,
        String memberName,
        String gatheringSubject,
        String gatheringDescription
) {
    @QueryProjection
    public ParticipantsResDto(Long memberId, String memberName, String gatheringSubject, String gatheringDescription) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.gatheringSubject = gatheringSubject;
        this.gatheringDescription = gatheringDescription;
    }
}
