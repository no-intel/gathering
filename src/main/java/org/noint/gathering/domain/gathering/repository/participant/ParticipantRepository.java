package org.noint.gathering.domain.gathering.repository.participant;

import org.noint.gathering.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
