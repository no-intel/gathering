package org.noint.gathering.domain.reservation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.noint.gathering.domain.reservation.dto.response.QRoomScheduleResDto;
import org.noint.gathering.domain.reservation.dto.response.RoomScheduleResDto;
import org.noint.gathering.entity.Gathering;
import org.noint.gathering.entity.QGathering;
import org.noint.gathering.entity.Reservation;
import org.noint.gathering.entity.RoomSchedule;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static org.noint.gathering.entity.QGathering.gathering;
import static org.noint.gathering.entity.QReservation.reservation;
import static org.noint.gathering.entity.QRoom.room;
import static org.noint.gathering.entity.QRoomSchedule.roomSchedule;
import static org.noint.gathering.entity.QTimeSlot.timeSlot;

@Repository
@RequiredArgsConstructor
public class ReservationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<RoomScheduleResDto> findRoomSchedule(LocalDate date) {
        return queryFactory
                .select(new QRoomScheduleResDto(
                        roomSchedule.id,
                        room.id,
                        room.name,
                        room.capacity,
                        room.timeRate,
                        timeSlot.id,
                        timeSlot.time,
                        roomSchedule.date,
                        roomSchedule.isAble
                )).from(roomSchedule)
                .innerJoin(roomSchedule.room, room)
                .innerJoin(roomSchedule.timeSlot, timeSlot)
                .where(roomSchedule.date.eq(date))
                .orderBy(room.timeRate.desc(), timeSlot.id.asc())
                .fetch();
    }

    public List<Reservation> findAllByGatheringOrRoomSchedules(Gathering gathering, List<RoomSchedule> roomSchedules) {
        return queryFactory
                .selectFrom(reservation)
                .where(reservation.gathering.eq(gathering)
                        .or(reservation.roomSchedule.in(roomSchedules))
                )
                .fetch();
    }

    public List<Reservation> findAllByRequestId(String requestId) {
        return queryFactory
                .selectFrom(reservation)
                .innerJoin(reservation.gathering, gathering).fetchJoin()
                .innerJoin(reservation.roomSchedule, roomSchedule).fetchJoin()
                .where(reservation.requestId.eq(requestId))
                .fetch();
    }
}
