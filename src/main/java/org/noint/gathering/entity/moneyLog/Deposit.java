package org.noint.gathering.entity.moneyLog;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.noint.gathering.entity.Member;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Deposit extends MoneyLog {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sender_member_id")
    private Member sender;

    public Deposit(BigDecimal amount, BigDecimal totalMoney, Member member, Member sender) {
        super(amount, totalMoney, member);
        this.sender = sender;
    }
}
