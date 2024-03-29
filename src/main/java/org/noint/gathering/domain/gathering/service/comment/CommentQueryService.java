package org.noint.gathering.domain.gathering.service.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.gathering.dto.response.CommentsResDto;
import org.noint.gathering.domain.gathering.exception.GatheringException;
import org.noint.gathering.domain.gathering.repository.comment.CommentQueryRepository;
import org.noint.gathering.domain.gathering.repository.comment.CommentRepository;
import org.noint.gathering.domain.gathering.service.gathering.GatheringQueryService;
import org.noint.gathering.domain.gathering.service.participant.ParticipantQueryService;
import org.noint.gathering.domain.member.service.MemberQueryService;
import org.noint.gathering.entity.Comment;
import org.noint.gathering.entity.Gathering;
import org.noint.gathering.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.noint.gathering.domain.gathering.enums.GatheringExceptionBody.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentQueryService {

    private final MemberQueryService memberQueryService;

    private final GatheringQueryService gatheringQueryService;

    private final ParticipantQueryService participantQueryService;

    private final CommentQueryRepository commentQueryRepository;

    private final CommentRepository commentRepository;

    public Slice<CommentsResDto> getComments(Long memberId, Long gatheringId, Pageable pageable) {
        Member member = memberQueryService.getMember(memberId);
        Gathering gathering = gatheringQueryService.getGathering(gatheringId);
        checkEntryMember(gathering, member);
        return commentQueryRepository.getComments(gatheringId, pageable);
    }

    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new GatheringException(NOT_FOUND_COMMENT));
    }

    private void checkEntryMember(Gathering gathering, Member member) {
        if (!participantQueryService.isEntry(gathering, member)) {
            log.warn("모임 참여자가 아니므로 댓글을 불러올 권한이 없습니다.");
            throw new GatheringException(FORBIDDEN);
        }
    }
}
