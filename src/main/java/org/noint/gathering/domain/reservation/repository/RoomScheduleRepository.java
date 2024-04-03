package org.noint.gathering.domain.reservation.repository;

import jakarta.persistence.LockModeType;
import org.noint.gathering.entity.RoomSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomScheduleRepository extends JpaRepository<RoomSchedule, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT rs FROM RoomSchedule rs WHERE rs.id IN (:ids)")
    List<RoomSchedule> findAllByIdForUpdate(@Param("ids") Iterable<Long> ids);
}
