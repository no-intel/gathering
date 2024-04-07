package org.noint.gathering.domain.reservation.service;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.noint.gathering.domain.gathering.service.gathering.GatheringQueryService;
import org.noint.gathering.domain.reservation.dto.request.ReserveGatheringReqDto;
import org.noint.gathering.domain.reservation.exception.ReservationException;
import org.noint.gathering.domain.reservation.repository.ReservationRepository;
import org.noint.gathering.entity.Progress;
import org.noint.gathering.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReservationCommendServiceTest {

    @Autowired ReservationCommendService reservationCommendService;

    @Autowired ReservationRepository reservationRepository;

    @Autowired GatheringQueryService gatheringQueryService;

    @Autowired ReservationQueryService reservationQueryService;

    @Test
    void 모임_장소_예약() throws Exception {
        //given
        Long memberId = 1L;
        Long gatheringId = 1L;
        String requestId = UUID.randomUUID().toString();
        List<Long> roomScheduleIds = new ArrayList<>() {{
            add(1L);
            add(2L);
            add(3L);
            add(4L);
            add(5L);
        }};
        ReserveGatheringReqDto request = new ReserveGatheringReqDto(requestId, gatheringId, roomScheduleIds);

        //when
        reservationCommendService.reserveGathering(memberId, request);
        List<Reservation> reservations = reservationRepository.findAll();

        //then
        assertThat(reservations)
                .extracting("roomSchedule")
                .extracting("id").containsAll(roomScheduleIds);
    }

    @Test
    void 모임_장소_예약_실패_권한_없음() throws Exception {
        //given
        Long memberId = 2L;
        Long gatheringId = 1L;
        String requestId = UUID.randomUUID().toString();
        List<Long> roomScheduleIds = new ArrayList<>() {{
            add(1L);
            add(2L);
            add(3L);
            add(4L);
            add(5L);
        }};
        ReserveGatheringReqDto request = new ReserveGatheringReqDto(requestId, gatheringId, roomScheduleIds);

        //when
        ThrowingCallable throwable = () -> reservationCommendService.reserveGathering(memberId, request);

        //then
        assertThatThrownBy(throwable).isInstanceOf(ReservationException.class);
    }

    @Test
    void 모임_장소_예약_실패_예약_된_모임() throws Exception {
        //given
        Long memberId = 1L;
        Long gatheringId = 1L;
        String requestId = UUID.randomUUID().toString();
        List<Long> roomScheduleIds = new ArrayList<>() {{
            add(1L);
            add(2L);
            add(3L);
            add(4L);
            add(5L);
        }};
        ReserveGatheringReqDto request = new ReserveGatheringReqDto(requestId, gatheringId, roomScheduleIds);
        reservationRepository.save(
                new Reservation(
                        UUID.randomUUID().toString(),
                        gatheringQueryService.getGathering(gatheringId),
                        reservationQueryService.getRoomSchedule(10L))
        );

        //when
        ThrowingCallable throwable = () -> reservationCommendService.reserveGathering(memberId, request);

        //then
        assertThatThrownBy(throwable).isInstanceOf(ReservationException.class);
    }

    @Test
    void 모임_장소_예약_실패_예약_스케쥴() throws Exception {
        //given
        Long memberId = 1L;
        Long gatheringId = 1L;
        String requestId = UUID.randomUUID().toString();
        List<Long> roomScheduleIds = new ArrayList<>() {{
            add(1L);
            add(2L);
            add(3L);
            add(4L);
            add(5L);
        }};
        ReserveGatheringReqDto request = new ReserveGatheringReqDto(requestId, gatheringId, roomScheduleIds);
        reservationRepository.save(
                new Reservation(
                        UUID.randomUUID().toString(),
                        gatheringQueryService.getGathering(2L),
                        reservationQueryService.getRoomSchedule(1L))
        );

        //when
        ThrowingCallable throwable = () -> reservationCommendService.reserveGathering(memberId, request);

        //then
        assertThatThrownBy(throwable).isInstanceOf(ReservationException.class);
    }

    @Test
    void 모임_장소_예약_실패_중복_요청() throws Exception {
        //given
        Long memberId = 1L;
        Long gatheringId = 1L;
        String requestId = UUID.randomUUID().toString();
        List<Long> roomScheduleIds = new ArrayList<>() {{
            add(1L);
            add(2L);
            add(3L);
            add(4L);
            add(5L);
        }};
        ReserveGatheringReqDto request = new ReserveGatheringReqDto(requestId, gatheringId, roomScheduleIds);
        reservationRepository.save(
                new Reservation(
                        requestId,
                        gatheringQueryService.getGathering(2L),
                        reservationQueryService.getRoomSchedule(1L))
        );

        //when
        ThrowingCallable throwable = () -> reservationCommendService.reserveGathering(memberId, request);

        //then
        assertThatThrownBy(throwable).isInstanceOf(ReservationException.class);
    }

    @Test
    void 모임_예약_취소() throws Exception {
        Long memberId = 1L;
        Long gatheringId = 1L;
        String requestId = UUID.randomUUID().toString();
        List<Long> roomScheduleIds = new ArrayList<>() {{
            add(1L);
            add(2L);
            add(3L);
            add(4L);
            add(5L);
        }};
        ReserveGatheringReqDto request = new ReserveGatheringReqDto(requestId, gatheringId, roomScheduleIds);
        reservationCommendService.reserveGathering(memberId, request);

        //when
        reservationCommendService.cancelReservation(memberId, requestId);
        List<Reservation> reservations = reservationQueryService.getAllByRequestId(requestId);

        //then
        assertThat(reservations.size()).isEqualTo(roomScheduleIds.size());
        assertThat(reservations).extracting("progress").isNotIn(Progress.PROGRESS);
    }

    @Test
    void 모임_예약_취소_실패_권한_없음() throws Exception {
        Long memberId = 1L;
        Long gatheringId = 1L;
        String requestId = UUID.randomUUID().toString();
        List<Long> roomScheduleIds = new ArrayList<>() {{
            add(1L);
            add(2L);
            add(3L);
            add(4L);
            add(5L);
        }};
        ReserveGatheringReqDto request = new ReserveGatheringReqDto(requestId, gatheringId, roomScheduleIds);
        reservationCommendService.reserveGathering(memberId, request);

        //when
        ThrowingCallable throwable = () -> reservationCommendService.cancelReservation(2L, requestId);

        //then
        assertThatThrownBy(throwable).isInstanceOf(ReservationException.class);
    }

    @Test
    void 모임_예약_취소_실패_요청_ID_다름() throws Exception {
        Long memberId = 1L;
        Long gatheringId = 1L;
        String requestId = UUID.randomUUID().toString();
        List<Long> roomScheduleIds = new ArrayList<>() {{
            add(1L);
            add(2L);
            add(3L);
            add(4L);
            add(5L);
        }};
        ReserveGatheringReqDto request = new ReserveGatheringReqDto(requestId, gatheringId, roomScheduleIds);
        reservationCommendService.reserveGathering(memberId, request);

        //when
        ThrowingCallable throwable = () -> reservationCommendService.cancelReservation(memberId, UUID.randomUUID().toString());

        //then
        assertThatThrownBy(throwable).isInstanceOf(ReservationException.class);
    }
}