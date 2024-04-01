package org.noint.gathering.domain.reservation.repository;

import org.noint.gathering.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
