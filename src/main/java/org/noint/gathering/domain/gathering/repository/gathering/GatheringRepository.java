package org.noint.gathering.domain.gathering.repository.gathering;

import org.noint.gathering.entity.Gathering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatheringRepository extends JpaRepository<Gathering, Long> {
}
