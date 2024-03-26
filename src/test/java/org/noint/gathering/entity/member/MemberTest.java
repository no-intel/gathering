package org.noint.gathering.entity.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.noint.gathering.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.noint.gathering.entity.QMember.member;

@SpringBootTest @TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class MemberTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeAll
    void before() {
        this.queryFactory = new JPAQueryFactory(em);
    }

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

}