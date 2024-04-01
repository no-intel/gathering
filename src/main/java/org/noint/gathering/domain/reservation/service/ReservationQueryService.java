package org.noint.gathering.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.reservation.dto.response.RoomScheduleResDto;
import org.noint.gathering.domain.reservation.exception.ReservationException;
import org.noint.gathering.domain.reservation.repository.ReservationQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.noint.gathering.domain.reservation.enums.ReservationExceptionBody.OUT_OF_RANGE_DATE;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationQueryService {

    private final ReservationQueryRepository reservationQueryRepository;

    private final static Integer MAX_RANGE_VAL = 7;

    private final static Integer MIN_RANGE_VAL = 1;


    public List<RoomScheduleResDto> getDayRoomSchedule(LocalDate date) {
        checkDateRange(date);
        return reservationQueryRepository.findRoomSchedule(date);
    }

    private static void checkDateRange(LocalDate date) {
        LocalDate now = LocalDate.now();
        if (date.isAfter(now.plusDays(MAX_RANGE_VAL)) || date.isBefore(now.plusDays(MIN_RANGE_VAL))) {
            log.info("검색 가능한 날짜를 벗어 났습니다.");
            throw new ReservationException(OUT_OF_RANGE_DATE);
        }
    }
}
