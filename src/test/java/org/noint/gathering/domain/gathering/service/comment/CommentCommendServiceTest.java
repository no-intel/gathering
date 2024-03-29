package org.noint.gathering.domain.gathering.service.comment;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.noint.gathering.domain.gathering.dto.request.CommentBodyReqDto;
import org.noint.gathering.domain.gathering.dto.request.GatheringReqDto;
import org.noint.gathering.domain.gathering.exception.GatheringException;
import org.noint.gathering.domain.gathering.repository.comment.CommentRepository;
import org.noint.gathering.domain.gathering.service.gathering.GatheringCommendService;
import org.noint.gathering.domain.member.dto.request.RegisterReqDto;
import org.noint.gathering.domain.member.service.MemberCommendService;
import org.noint.gathering.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class CommentCommendServiceTest {

    @Autowired CommentCommendService commentCommendService;

    @Autowired MemberCommendService memberCommendService;

    @Autowired GatheringCommendService gatheringCommendService;

    @Autowired CommentRepository commentRepository;

    @Autowired EntityManager em;

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
        em.flush();
        em.clear();
        System.out.println("=========================================");
    }
    @Test
    void 모임_댓글_작성() throws Exception {
        //given
        Long memberId = 1L;
        Long gatheringId = 1L;
        String body = "저는 토요일만 아니면 됩니당~";

        //when
        commentCommendService.writeComment(memberId, gatheringId, body);
        Comment comment = commentRepository.findById(1L).get();

        //then
        assertThat(comment.getBody()).isEqualTo(body);
    }

    @Test
    void 모임_댓글_작성_실패_권한_없음() throws Exception {
        //given
        Long memberId = 9L;
        Long gatheringId = 1L;
        String body = "저는 토요일만 아니면 됩니당~";

        //when
        ThrowingCallable throwable = () -> commentCommendService.writeComment(memberId, gatheringId, body);

        //then
        assertThatThrownBy(throwable).isInstanceOf(GatheringException.class);
    }

    @Test
    void 댓글_수정_성공() throws Exception {
        //given
        Long memberId = 1L;
        Long gatheringId = 1L;
        String body = "저는 토요일만 아니면 됩니당~";
        String newBody = "토요일도 될 것 같아용~";

        //when
        em.createNativeQuery("insert into comment(body, gathering_id, member_id, create_date, last_modified_date) values (?, ?, ?, ?, ?)")
                .setParameter(1, body)
                .setParameter(2, gatheringId)
                .setParameter(3, memberId)
                .setParameter(4, LocalDateTime.now())
                .setParameter(5, LocalDateTime.now())
                .executeUpdate();
        em.flush();
        em.clear();

        commentCommendService.updateComment(memberId, 1L, new CommentBodyReqDto(newBody));
        em.flush();
        em.clear();

        Comment comment = commentRepository.findById(1L).get();

        //then
        assertThat(comment.getBody()).isEqualTo(newBody);
        assertThat(comment.getCreateDate()).isNotEqualTo(comment.getLastModifiedDate());
    }

    @Test
    void 댓글_수정_실패_권한_없음() throws Exception {
        //given
        Long memberId = 1L;
        Long gatheringId = 1L;
        String body = "저는 토요일만 아니면 됩니당~";
        String newBody = "토요일도 될 것 같아용~";

        //when
        em.createNativeQuery("insert into comment(body, gathering_id, member_id, create_date, last_modified_date) values (?, ?, ?, ?, ?)")
                .setParameter(1, body)
                .setParameter(2, gatheringId)
                .setParameter(3, memberId)
                .setParameter(4, LocalDateTime.now())
                .setParameter(5, LocalDateTime.now())
                .executeUpdate();
        em.flush();
        em.clear();

        ThrowingCallable throwable = () -> commentCommendService.updateComment(2L, 1L, new CommentBodyReqDto(newBody));

        //then
        assertThatThrownBy(throwable).isInstanceOf(GatheringException.class);
    }

    @Test
    void 댓글_수정_실패_댓글_없음() throws Exception {
        //given
        Long memberId = 1L;
        Long gatheringId = 1L;
        String body = "저는 토요일만 아니면 됩니당~";
        String newBody = "토요일도 될 것 같아용~";

        //when
        em.createNativeQuery("insert into comment(body, gathering_id, member_id, create_date, last_modified_date) values (?, ?, ?, ?, ?)")
                .setParameter(1, body)
                .setParameter(2, gatheringId)
                .setParameter(3, memberId)
                .setParameter(4, LocalDateTime.now())
                .setParameter(5, LocalDateTime.now())
                .executeUpdate();
        em.flush();
        em.clear();

        ThrowingCallable throwable = () -> commentCommendService.updateComment(memberId, 2L, new CommentBodyReqDto(newBody));

        //then
        assertThatThrownBy(throwable).isInstanceOf(GatheringException.class);
    }
}