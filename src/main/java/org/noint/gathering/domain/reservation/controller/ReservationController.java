package org.noint.gathering.domain.reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.reservation.dto.request.CancelReservationReqDto;
import org.noint.gathering.domain.reservation.dto.request.ReserveGatheringReqDto;
import org.noint.gathering.domain.reservation.dto.response.RoomScheduleResDto;
import org.noint.gathering.domain.reservation.service.ReservationCommendService;
import org.noint.gathering.domain.reservation.service.ReservationQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationQueryService reservationQueryService;

    private final ReservationCommendService reservationCommendService;

    @GetMapping("/room-schedule/{date}")
    public ResponseEntity<List<RoomScheduleResDto>> getRoomSchedule(@PathVariable("date") LocalDate date) {
        log.info("날짜별 룸 스케줄 목록 조회 API");
        return new ResponseEntity<>(reservationQueryService.getDayRoomSchedule(date), HttpStatus.OK);
    }

    @PostMapping("/reservation")
    public ResponseEntity<Void> reserveGathering(@RequestAttribute("memberId") Long memberId,
                                                 @Valid @RequestBody ReserveGatheringReqDto request) {
        log.info("모임 예약 API");
        reservationCommendService.reserveGathering(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/reservation")
    public ResponseEntity<Void> cancelReservation(@RequestAttribute("memberId") Long memberId,
                                                  @Valid @RequestBody CancelReservationReqDto request) {
        log.info("모임 예약 취소 API");
        reservationCommendService.cancelReservation(memberId, request.requestId());
        return ResponseEntity.noContent().build();
    }
}
