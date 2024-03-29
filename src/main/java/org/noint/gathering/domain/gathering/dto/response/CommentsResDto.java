package org.noint.gathering.domain.gathering.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record CommentsResDto(
        Long writerId,
        String writerName,
        String body,
        LocalDateTime createDate,
        LocalDateTime lastModifiedDate
) {
    @QueryProjection
    public CommentsResDto(Long writerId, String writerName, String body, LocalDateTime createDate, LocalDateTime lastModifiedDate) {
        this.writerId = writerId;
        this.writerName = writerName;
        this.body = body;
        this.createDate = createDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
