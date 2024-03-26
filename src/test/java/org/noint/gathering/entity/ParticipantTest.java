package org.noint.gathering.entity;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.noint.gathering.entity.QParticipant.participant;

@SpringBootTest
@Transactional
class ParticipantTest {

    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    public void 모임_멤버_엔티티_테스트() throws Exception {
        Member user = new Member("a@b.c", "유저", "password");
        em.persist(user);
        Gathering newGathering = new Gathering("모각코", "각자 모여 코딩합시다", 4, user);
        em.persist(newGathering);
        Participant newParticipant = new Participant(newGathering, user);
        em.persist(newParticipant);
        em.flush();
        em.clear();

        List<Participant> participants = queryFactory
                .selectFrom(participant)
                .fetch();

        Member member = participants.getFirst().getMember();
        Gathering gathering = participants.getFirst().getGathering();

        assertThat(participants.size()).isEqualTo(1);
        assertThat(member.getName()).isEqualTo(user.getName());
        assertThat(gathering.getSubject()).isEqualTo(newGathering.getSubject());
    }
}