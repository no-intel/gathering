package org.noint.gathering.domain.member.repository;

import org.noint.gathering.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
