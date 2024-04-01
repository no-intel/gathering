package org.noint.gathering.domain.reservation.service;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.noint.gathering.domain.reservation.dto.request.RoomScheduleReqDto;
import org.noint.gathering.domain.reservation.dto.response.RoomScheduleResDto;
import org.noint.gathering.domain.reservation.exception.ReservationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    void 룸_스케줄_조회() throws Exception {
        //given
        LocalDate date = LocalDate.now().plusDays(1);
        RoomScheduleReqDto request = new RoomScheduleReqDto(date);

        //when
        List<RoomScheduleResDto> dayRoomSchedule = reservationQueryService.getDayRoomSchedule(request);

        //then
        assertThat(dayRoomSchedule).extracting("date").contains(date);
    }

    @Test
    void 룸_스케줄_조회_실패_범위_오버() throws Exception {
        //given
        LocalDate date = LocalDate.now().plusDays(8);
        LocalDate now = LocalDate.now();
        RoomScheduleReqDto request1 = new RoomScheduleReqDto(date);
        RoomScheduleReqDto request2 = new RoomScheduleReqDto(now);

        //when
        ThrowingCallable throwable1 = () -> reservationQueryService.getDayRoomSchedule(request1);
        ThrowingCallable throwable2 = () -> reservationQueryService.getDayRoomSchedule(request2);

        //then
        assertThatThrownBy(throwable1).isInstanceOf(ReservationException.class);
        assertThatThrownBy(throwable2).isInstanceOf(ReservationException.class);
    }
}