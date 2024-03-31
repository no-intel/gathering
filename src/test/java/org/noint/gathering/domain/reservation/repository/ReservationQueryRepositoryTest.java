package org.noint.gathering.domain.reservation.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.noint.gathering.domain.reservation.dto.response.RoomScheduleResDto;
import org.noint.gathering.entity.RoomSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReservationQueryRepositoryTest {

    @Autowired
    ReservationQueryRepository reservationQueryRepository;

    @Test
    void 방_스케줄_목록() throws Exception {
        //given
        LocalDate date = LocalDate.now().plusDays(1);

        //when
        List<RoomScheduleResDto> roomSchedule = reservationQueryRepository.findRoomSchedule(date);

        //then
        assertThat(roomSchedule.size()).isEqualTo(70);
    }
}