package org.noint.gathering.entity.moneyLog;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.noint.gathering.entity.Member;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Charge extends MoneyLog {
    private String requestId;

    public Charge(BigDecimal amount, BigDecimal totalMoney, Member member, String requestId) {
        super(amount, totalMoney, member);
        this.requestId = requestId;
    }
}
