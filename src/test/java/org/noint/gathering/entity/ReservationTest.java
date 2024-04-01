package org.noint.gathering.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.noint.gathering.entity.QReservation.reservation;

@SpringBootTest
@Transactional
class ReservationTest {

    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    public void 예약_엔티티_테스트() throws Exception {
        Room diamondRoom = new Room("다이아 룸", 10, BigDecimal.valueOf(20000));
        em.persist(diamondRoom);
        TimeSlot time = new TimeSlot("10:00 ~ 11:50");
        em.persist(time);
        RoomSchedule newSchedule = new RoomSchedule(LocalDate.now(), diamondRoom, time);
        em.persist(newSchedule);
        Member user = new Member("a@b.c", "유저", "password");
        em.persist(user);
        Gathering newGathering = new Gathering("모각코", "각자 모여 코딩합시다", 4, user);
        em.persist(newGathering);
        Reservation newReservation = new Reservation(UUID.randomUUID().toString(), newGathering, newSchedule);
        em.persist(newReservation);

        em.flush();
        em.clear();

        List<Reservation> reservations = queryFactory
                .selectFrom(reservation)
                .fetch();
        Member member = reservations.getFirst().getGathering().getMember();

        assertThat(reservations.size()).isEqualTo(1);
        assertThat(member.getName()).isEqualTo("유저");
    }
}