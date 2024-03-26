package org.noint.gathering.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.noint.gathering.entity.QRoomSchedule.roomSchedule;

@SpringBootTest
@Transactional
class RoomScheduleTest {
    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    public void 방_시간표_엔티티_테스트() throws Exception {
        Room diamondRoom = new Room("다이아 룸", 10, BigDecimal.valueOf(20000));
        em.persist(diamondRoom);
        TimeSlot time = new TimeSlot("10:00 ~ 11:50");
        em.persist(time);
        RoomSchedule newSchedule = new RoomSchedule(diamondRoom, time);
        em.persist(newSchedule);
        em.flush();
        em.clear();

        List<RoomSchedule> roomSchedules = queryFactory
                .selectFrom(roomSchedule)
                .fetch();

        Room room = roomSchedules.getFirst().getRoom();
        TimeSlot timeSlot = roomSchedules.getFirst().getTimeSlot();

        assertThat(roomSchedules.size()).isEqualTo(1);
        assertThat(room.getName()).isEqualTo("다이아 룸");
        assertThat(timeSlot.getTime()).isEqualTo("10:00 ~ 11:50");
    }
}