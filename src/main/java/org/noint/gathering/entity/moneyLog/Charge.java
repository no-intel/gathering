package org.noint.gathering.entity.moneyLog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.noint.gathering.entity.Member;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Charge extends MoneyLog {

    @Column(length = 36, nullable = false)
    private String chargeId;

    public Charge(BigDecimal amount, BigDecimal totalMoney, Member member, String chargeId) {
        super(amount, totalMoney, member);
        this.chargeId = chargeId;
    }
}
