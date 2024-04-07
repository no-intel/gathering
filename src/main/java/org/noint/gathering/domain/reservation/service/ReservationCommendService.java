package org.noint.gathering.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.gathering.service.gathering.GatheringQueryService;
import org.noint.gathering.domain.reservation.dto.request.ReserveGatheringReqDto;
import org.noint.gathering.domain.reservation.exception.ReservationException;
import org.noint.gathering.domain.reservation.repository.ReservationRepository;
import org.noint.gathering.entity.Gathering;
import org.noint.gathering.entity.Progress;
import org.noint.gathering.entity.Reservation;
import org.noint.gathering.entity.RoomSchedule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.noint.gathering.domain.reservation.enums.ReservationExceptionBody.*;
import static org.noint.gathering.entity.AbleStatus.DISABLED;
import static org.noint.gathering.entity.AbleStatus.ENABLED;
import static org.noint.gathering.entity.Progress.CANCELED;
import static org.noint.gathering.entity.Progress.PROGRESS;

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
        List<RoomSchedule> roomSchedules = reservationQueryService.getRoomSchedulesForUpdate(request.roomScheduleIds());
        checkAbleReserve(gathering, roomSchedules);

        List<Reservation> reservations = new ArrayList<>();
        for (RoomSchedule roomSchedule : roomSchedules) {
            reservations.add(new Reservation(request.requestId(), gathering, roomSchedule));
            roomSchedule.updateIsAble(DISABLED);
        }
        reservationRepository.saveAll(reservations);
        reservationQueryService.updateDayRoomSchedule(reservations.getFirst().getRoomSchedule().getDate());
    }

    public void cancelReservation(Long memberId, String requestId) {
        List<Reservation> reservations = reservationQueryService.getAllByRequestId(requestId);
        checkCancelAble(memberId, reservations);

        reservations.forEach(reservation -> {
            reservation.updateProgress(CANCELED);
            reservation.getRoomSchedule().updateIsAble(ENABLED);
        });
        reservationQueryService.updateDayRoomSchedule(reservations.getFirst().getRoomSchedule().getDate());
    }

    private void checkCancelAble(Long memberId, List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            log.info("예약 요청 ID를 찾을 수 없습니다.");
            throw new ReservationException(WRONG_REQUEST_ID);
        }
        boolean isGatheringCreator = reservations.getFirst().getGathering().getMember().getId().equals(memberId);
        if (!isGatheringCreator) {
            log.info("예약 취소는 모임 개설자만 가능합니다.");
            throw new ReservationException(FORBIDDEN);
        }
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

        for (Reservation reservation : reservations) {
            if (reservation.getProgress() == PROGRESS) {
                throw new ReservationException(UNAVAILABLE_RESERVE);
            }
        }
    }
}
