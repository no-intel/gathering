package org.noint.gathering.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.noint.gathering.entity.QGathering.gathering;

@SpringBootTest
@Transactional
class GatheringTest {

    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    public void 모임_엔티티_테스트() throws Exception {
        Member user = new Member("a@b.c", "유저", "password");
        em.persist(user);
        Gathering newGathering = new Gathering("모각코", "각자 모여 코딩합시다", 4, user);
        em.persist(newGathering);

        em.flush();
        em.clear();

        List<Gathering> gatherings = queryFactory
                .selectFrom(gathering)
                .fetch();

        Member creator = gatherings.getFirst().getMember();

        assertThat(gatherings.size()).isEqualTo(1);
        assertThat(gatherings).extracting("subject").contains(newGathering.getSubject());
        assertThat(creator.getName()).isEqualTo(user.getName());
    }
}