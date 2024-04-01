package org.noint.gathering.domain.reservation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.reservation.dto.response.RoomScheduleResDto;
import org.noint.gathering.domain.reservation.service.ReservationQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationQueryService reservationQueryService;

    @GetMapping("/room-schedule/{date}")
    public ResponseEntity<List<RoomScheduleResDto>> getRoomSchedule(@PathVariable("date") LocalDate date) {
        log.info("날짜별 룸 스케줄 목록 조회 API");
        return new ResponseEntity<>(reservationQueryService.getDayRoomSchedule(date), HttpStatus.OK);
    }
}
