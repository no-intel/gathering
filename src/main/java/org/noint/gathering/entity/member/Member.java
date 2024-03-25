package org.noint.gathering.entity.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.noint.gathering.entity.BaseTimeEntity;

import java.math.BigDecimal;

import static org.noint.gathering.entity.member.MemberStatus.REGISTERED;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue
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
}
