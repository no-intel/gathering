package org.noint.gathering.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.noint.gathering.entity.QTimeSlot.timeSlot;

@SpringBootTest
@Transactional
class TimeSlotTest {

    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    public void 예약_시간_엔티티_테스트() throws Exception {
        TimeSlot time = new TimeSlot("10:00 ~ 11:50");
        em.persist(time);
        em.flush();
        em.clear();

        List<TimeSlot> timeSlots = queryFactory
                .selectFrom(timeSlot)
                .fetch();

        assertThat(timeSlots).extracting("time").contains("10:00 ~ 11:50");
    }
}