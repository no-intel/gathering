package org.noint.gathering.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.noint.gathering.entity.QMember.member;

@SpringBootTest
@Transactional
class MemberTest {

    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    public void 멤버_엔티티_테스트() throws Exception {
        Member user = new Member("a@b.c", "유저", "password");
        em.persist(user);
        em.flush();
        em.clear();

        List<Member> findMembers = queryFactory
                .selectFrom(member)
                .fetch();

        assertThat(findMembers.size()).isEqualTo(1);
        assertThat(findMembers).extracting("name").contains("유저");
    }

    @Test
    void 계정_상태값_테스트() throws Exception {
        Member user = new Member("a@b.c", "유저", "password");
        em.persist(user);
        em.flush();
        em.clear();

        Member findMember = queryFactory
                .selectFrom(member)
                .fetchOne();

        boolean isActiveMember = findMember.isActiveMember();

        assertThat(isActiveMember).isTrue();
    }
}