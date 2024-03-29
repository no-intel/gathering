package org.noint.gathering.domain.gathering.service.comment;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.noint.gathering.domain.gathering.dto.request.GatheringReqDto;
import org.noint.gathering.domain.gathering.dto.response.CommentsResDto;
import org.noint.gathering.domain.gathering.exception.GatheringException;
import org.noint.gathering.domain.gathering.service.gathering.GatheringCommendService;
import org.noint.gathering.domain.member.dto.request.RegisterReqDto;
import org.noint.gathering.domain.member.service.MemberCommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class CommentQueryServiceTest {

    @Autowired
    CommentQueryService commentQueryService;

    @Autowired
    CommentCommendService commentCommendService;

    @Autowired
    MemberCommendService memberCommendService;

    @Autowired
    GatheringCommendService gatheringCommendService;

    @BeforeEach
    void beforeEach() {
        System.out.println("=================Before==================");
        for (int i = 0; i < 10; i++) {
            memberCommendService.register(new RegisterReqDto(i+"test@b.c", "test"+i, "password1"));
        }
        gatheringCommendService.createGathering(1L, new GatheringReqDto("모임 주제1", "설명1", 10));
        gatheringCommendService.entryGathering(2L, 1L);
        gatheringCommendService.entryGathering(3L, 1L);
        gatheringCommendService.entryGathering(4L, 1L);
        gatheringCommendService.entryGathering(5L, 1L);

        for (int i = 0; i < 5; i++) {
            commentCommendService.writeComment((long) (i+1), 1L, "comment"+i);
        }
        System.out.println("=========================================");
    }

    @Test
    void 모임_댓글_가져오기() throws Exception {
        //given
        Long memberId = 1L;
        Long gatheringId = 1L;
        PageRequest firstPageRequest = PageRequest.of(0, 3);
        PageRequest secondPageRequest = PageRequest.of(1, 3);

        //when
        Slice<CommentsResDto> firstPageComments = commentQueryService.getComments(memberId, gatheringId, firstPageRequest);
        Slice<CommentsResDto> secondPageComments = commentQueryService.getComments(memberId, gatheringId, secondPageRequest);

        //then
        assertThat(firstPageComments.getContent().size()).isEqualTo(firstPageRequest.getPageSize() + 1);
        assertThat(firstPageComments.getContent().getLast()).isEqualTo(secondPageComments.getContent().getFirst());
    }

    @Test
    void 모임_댓글_가져오기_실패_권한_없음() throws Exception {
        //given
        Long memberId = 9L;
        Long gatheringId = 1L;
        PageRequest pageRequest = PageRequest.of(0, 3);

        //when
        ThrowingCallable throwable = () -> commentQueryService.getComments(memberId, gatheringId, pageRequest);

        //then
        assertThatThrownBy(throwable).isInstanceOf(GatheringException.class);
    }
}