package org.noint.gathering.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.noint.gathering.entity.QComment.comment;

@SpringBootTest @TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CommentTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeAll
    void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    public void 코멘트_엔티티_테스트() throws Exception {
        Member user = new Member("a@b.c", "유저", "password");
        em.persist(user);
        Gathering newGathering = new Gathering("모각코", "각자 모여 코딩합시다", 4, user);
        em.persist(newGathering);
        Comment newComment = new Comment("몇 시에 예약할까요?", newGathering, user);
        em.persist(newComment);
        em.flush();
        em.clear();

        List<Comment> comments = queryFactory
                .selectFrom(comment)
                .fetch();

        Member commenter = comments.getFirst().getMember();
        Gathering gathering = comments.getFirst().getGathering();

        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments).extracting("body").contains(newComment.getBody());
        assertThat(commenter.getName()).isEqualTo(user.getName());
        assertThat(gathering.getSubject()).isEqualTo(newGathering.getSubject());
    }
}