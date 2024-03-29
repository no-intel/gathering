package org.noint.gathering.domain.gathering.service.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.gathering.enums.GatheringExceptionBody;
import org.noint.gathering.domain.gathering.exception.GatheringException;
import org.noint.gathering.domain.gathering.repository.comment.CommentRepository;
import org.noint.gathering.domain.gathering.service.gathering.GatheringQueryService;
import org.noint.gathering.domain.gathering.service.participant.ParticipantQueryService;
import org.noint.gathering.domain.member.service.MemberQueryService;
import org.noint.gathering.entity.Comment;
import org.noint.gathering.entity.Gathering;
import org.noint.gathering.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.noint.gathering.domain.gathering.enums.GatheringExceptionBody.FORBIDDEN;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentCommendService {

    private final CommentRepository commentRepository;

    private final MemberQueryService memberQueryService;

    private final GatheringQueryService gatheringQueryService;

    private final ParticipantQueryService participantQueryService;

    public void writeComment(Long memberId, Long gatheringId, String body) {
        Member member = memberQueryService.getMember(memberId);
        Gathering gathering = gatheringQueryService.getGathering(gatheringId);
        checkEntryMember(gathering, member);
        commentRepository.save(new Comment(body, gathering, member));
    }

    private void checkEntryMember(Gathering gathering, Member member) {
        if (!participantQueryService.isEntry(gathering, member)) {
            log.warn("모임 참여자가 아니므로 댓글 작성 권한이 없습니다.");
            throw new GatheringException(FORBIDDEN);
        }
    }
}
