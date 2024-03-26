package org.noint.gathering.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.noint.gathering.entity.QRoom.room;

@SpringBootTest
@Transactional
class RoomTest {

    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    public void 방_엔티티_테스트() throws Exception {
        Room diamondRoom = new Room("다이아 룸", 10, BigDecimal.valueOf(20000));
        em.persist(diamondRoom);
        em.flush();
        em.clear();

        List<Room> rooms = queryFactory
                .selectFrom(room)
                .fetch();

        assertThat(rooms).extracting("name").contains("다이아 룸");
    }
}