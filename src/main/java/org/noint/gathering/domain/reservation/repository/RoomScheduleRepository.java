package org.noint.gathering.domain.reservation.repository;

import org.noint.gathering.entity.RoomSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomScheduleRepository extends JpaRepository<RoomSchedule, Long> {
}
