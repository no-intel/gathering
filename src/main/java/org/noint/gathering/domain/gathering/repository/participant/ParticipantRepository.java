package org.noint.gathering.domain.gathering.repository.participant;

import org.noint.gathering.entity.Gathering;
import org.noint.gathering.entity.Member;
import org.noint.gathering.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Optional<Participant> findByGatheringAndMember(Gathering gathering, Member member);
}
