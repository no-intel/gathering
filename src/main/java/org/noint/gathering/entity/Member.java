package org.noint.gathering.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

import static org.noint.gathering.entity.MemberStatus.REGISTERED;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long Id;

    private String email;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    private BigDecimal money;

    public Member(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.status = REGISTERED;
        this.money = BigDecimal.ZERO;
    }

    public boolean isActiveMember() {
        MemberStatus status = this.getStatus();
        log.info("계정 상태 : " + status.name());
        return status == REGISTERED;
    }
}
