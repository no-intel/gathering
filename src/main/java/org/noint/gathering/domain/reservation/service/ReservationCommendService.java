package org.noint.gathering.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.gathering.service.gathering.GatheringQueryService;
import org.noint.gathering.domain.reservation.dto.request.ReserveGatheringReqDto;
import org.noint.gathering.domain.reservation.exception.ReservationException;
import org.noint.gathering.domain.reservation.repository.ReservationRepository;
import org.noint.gathering.entity.Gathering;
import org.noint.gathering.entity.Reservation;
import org.noint.gathering.entity.RoomSchedule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.noint.gathering.domain.reservation.enums.ReservationExceptionBody.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationCommendService {

    private final ReservationRepository reservationRepository;

    private final GatheringQueryService gatheringQueryService;

    private final ReservationQueryService reservationQueryService;

    public void reserveGathering(Long memberId, ReserveGatheringReqDto request) {
        checkDuplicateRequest(request);
        Gathering gathering = gatheringQueryService.getGathering(request.gatheringId());
        checkPermission(memberId, gathering);
        List<RoomSchedule> roomSchedules = reservationQueryService.getRoomSchedules(request.roomScheduleIds());
        checkAbleReserve(gathering, roomSchedules);

        List<Reservation> reservations = new ArrayList<>();
        for (RoomSchedule roomSchedule : roomSchedules) {
            reservations.add(new Reservation(request.requestId(), gathering, roomSchedule));
        }

        reservationRepository.saveAll(reservations);
    }

    private void checkDuplicateRequest(ReserveGatheringReqDto request) {
        List<Reservation> reservations = reservationQueryService.getAllByRequestId(request.requestId());
        if (!reservations.isEmpty()) {
            log.warn("모임 예약 요청 중복");
            throw new ReservationException(DUPLICATE_REQUEST);
        }
    }

    private void checkPermission(Long memberId, Gathering gathering) {
        boolean isNotAble = !gathering.getMember().getId().equals(memberId);
        if (isNotAble) {
            log.warn("룸 예약은 모임 개설자만 가능합니다.");
            throw new ReservationException(FORBIDDEN);
        }
    }
    private void checkAbleReserve(Gathering gathering, List<RoomSchedule> roomSchedules) {
        List<Reservation> reservations = reservationQueryService.getAllByGatheringOrRoomSchedules(gathering, roomSchedules);
        if (!reservations.isEmpty()) {
            throw new ReservationException(UNAVAILABLE_RESERVE);
        }
    }
}
