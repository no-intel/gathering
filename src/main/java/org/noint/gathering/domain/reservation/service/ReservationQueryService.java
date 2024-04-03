package org.noint.gathering.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.reservation.dto.response.RoomScheduleResDto;
import org.noint.gathering.domain.reservation.exception.ReservationException;
import org.noint.gathering.domain.reservation.repository.ReservationQueryRepository;
import org.noint.gathering.domain.reservation.repository.ReservationRepository;
import org.noint.gathering.domain.reservation.repository.RoomScheduleRepository;
import org.noint.gathering.entity.Gathering;
import org.noint.gathering.entity.Reservation;
import org.noint.gathering.entity.RoomSchedule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.noint.gathering.domain.reservation.enums.ReservationExceptionBody.NOT_FOUND_ROOM_SCHEDULE;
import static org.noint.gathering.domain.reservation.enums.ReservationExceptionBody.OUT_OF_RANGE_DATE;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationQueryService {

    private final ReservationQueryRepository reservationQueryRepository;

    private final RoomScheduleRepository roomScheduleRepository;

    private final ReservationRepository reservationRepository;

    private final static Integer MAX_RANGE_VAL = 7;

    private final static Integer MIN_RANGE_VAL = 1;


    public List<RoomScheduleResDto> getDayRoomSchedule(LocalDate date) {
        checkDateRange(date);
        return reservationQueryRepository.findRoomSchedule(date);
    }

    public RoomSchedule getRoomSchedule(Long roomScheduleId) {
        return roomScheduleRepository.findById(roomScheduleId)
                .orElseThrow(()-> new ReservationException(NOT_FOUND_ROOM_SCHEDULE));
    }

    public List<RoomSchedule> getRoomSchedules(List<Long> roomScheduleIds) {
        List<RoomSchedule> roomSchedules = roomScheduleRepository.findAllById(roomScheduleIds);
        if (roomSchedules.isEmpty()) {
            throw new ReservationException(NOT_FOUND_ROOM_SCHEDULE);
        }
        return roomSchedules;

    }

    public List<RoomSchedule> getRoomSchedulesForUpdate(List<Long> roomScheduleIds) {
        List<RoomSchedule> roomSchedules = roomScheduleRepository.findAllByIdForUpdate(roomScheduleIds);
        if (roomSchedules.isEmpty()) {
            throw new ReservationException(NOT_FOUND_ROOM_SCHEDULE);
        }
        return roomSchedules;

    }

    public List<Reservation> getAllByGatheringOrRoomSchedules(Gathering gathering, List<RoomSchedule> roomSchedules) {
        return reservationQueryRepository.findAllByGatheringOrRoomSchedules(gathering, roomSchedules);
    }

    public List<Reservation> getAllByRequestId(String requestId) {
        return reservationQueryRepository.findAllByRequestId(requestId);
    }

    private static void checkDateRange(LocalDate date) {
        LocalDate now = LocalDate.now();
        if (date.isAfter(now.plusDays(MAX_RANGE_VAL)) || date.isBefore(now.plusDays(MIN_RANGE_VAL))) {
            log.info("검색 가능한 날짜를 벗어 났습니다.");
            throw new ReservationException(OUT_OF_RANGE_DATE);
        }
    }

}
