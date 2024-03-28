package org.noint.gathering.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

import static org.noint.gathering.entity.MemberStatus.INACTIVE;
import static org.noint.gathering.entity.MemberStatus.REGISTERED;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UQ_email", columnNames = {"email"}),
        @UniqueConstraint(name = "UQ_name", columnNames = {"name"})
})
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long Id;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;

    @Column(precision = 6, scale = 0, nullable = false)
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

    public void resign() {
        this.status = INACTIVE;
    }
}
