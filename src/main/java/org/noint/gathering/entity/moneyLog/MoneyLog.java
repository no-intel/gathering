package org.noint.gathering.entity.moneyLog;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.noint.gathering.entity.BaseTimeEntity;
import org.noint.gathering.entity.Member;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoneyLog extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "money_log_id")
    private Long id;

    private BigDecimal amount;

    private BigDecimal totalMoney;

    @Column(insertable = false, updatable = false)
    private String dtype;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public MoneyLog(BigDecimal amount, BigDecimal totalMoney, Member member) {
        this.amount = amount;
        this.totalMoney = totalMoney;
        this.member = member;
    }
}
