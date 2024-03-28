package org.noint.gathering.domain.gathering.repository.participant;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.noint.gathering.domain.gathering.dto.response.ParticipantsResDto;
import org.noint.gathering.domain.gathering.dto.response.QParticipantsResDto;
import org.noint.gathering.entity.Gathering;
import org.noint.gathering.entity.Participant;
import org.noint.gathering.entity.QGathering;
import org.noint.gathering.entity.QMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.noint.gathering.entity.QGathering.gathering;
import static org.noint.gathering.entity.QMember.member;
import static org.noint.gathering.entity.QParticipant.participant;

@Repository
@RequiredArgsConstructor
public class ParticipantQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<ParticipantsResDto> findAllByGathering(Long gatheringId, Pageable pageable) {
        List<ParticipantsResDto> content = queryFactory
                .select(new QParticipantsResDto(
                        participant.member.Id,
                        participant.member.name,
                        participant.gathering.subject,
                        participant.gathering.description
                ))
                .from(participant)
                .join(participant.gathering, gathering).on(gathering.id.eq(gatheringId))
                .join(participant.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCountQuery = queryFactory
                .select(Wildcard.count)
                .from(participant)
                .join(participant.gathering, gathering).on(gathering.id.eq(gatheringId));

        return PageableExecutionUtils.getPage(content, pageable, totalCountQuery::fetchOne);
    }
}
