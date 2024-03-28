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
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UQ_charge_id", columnNames = {"charge_id"})
})
public class MoneyLog extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "money_log_id")
    private Long id;

    @Column(precision = 6, scale = 0, nullable = false)
    private BigDecimal amount;

    @Column(precision = 6, scale = 0, nullable = false)
    private BigDecimal totalMoney;

    @Column(length = 100, insertable = false, updatable = false)
    private String dtype;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private Member member;

    public MoneyLog(BigDecimal amount, BigDecimal totalMoney, Member member) {
        this.amount = amount;
        this.totalMoney = totalMoney;
        this.member = member;
    }
}
