package org.noint.gathering.domain.reservation.repository;

import org.noint.gathering.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
