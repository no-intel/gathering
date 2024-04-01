package org.noint.gathering.domain.reservation.service;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.noint.gathering.domain.reservation.dto.response.RoomScheduleResDto;
import org.noint.gathering.domain.reservation.exception.ReservationException;
import org.noint.gathering.entity.RoomSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReservationQueryServiceTest {

    @Autowired
    ReservationQueryService reservationQueryService;

    @Test
    void 날짜별_룸_스케줄_조회() throws Exception {
        //given
        LocalDate date = LocalDate.now().plusDays(1);

        //when
        List<RoomScheduleResDto> dayRoomSchedule = reservationQueryService.getDayRoomSchedule(date);

        //then
        assertThat(dayRoomSchedule).extracting("date").contains(date);
    }

    @Test
    void 날짜별_룸_스케줄_조회_실패_범위_오버() throws Exception {
        //given
        LocalDate date = LocalDate.now().plusDays(8);
        LocalDate now = LocalDate.now();

        //when
        ThrowingCallable throwable1 = () -> reservationQueryService.getDayRoomSchedule(date);
        ThrowingCallable throwable2 = () -> reservationQueryService.getDayRoomSchedule(now);

        //then
        assertThatThrownBy(throwable1).isInstanceOf(ReservationException.class);
        assertThatThrownBy(throwable2).isInstanceOf(ReservationException.class);
    }


    @Test
    void 룸_스케줄_목록_조회() throws Exception {
        //given
        List<Long> roomScheduleIds = new ArrayList<>() {{
            add(1L);
            add(2L);
            add(3L);
            add(4L);
            add(5L);
        }};

        //when
        List<RoomSchedule> roomSchedules = reservationQueryService.getRoomSchedules(roomScheduleIds);

        //then
        assertThat(roomSchedules.size()).isEqualTo(roomScheduleIds.size());
    }
}