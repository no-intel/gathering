package org.noint.gathering.entity.moneyLog;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.noint.gathering.entity.Member;
import org.noint.gathering.entity.Reservation;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservations extends MoneyLog {
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reservation_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private Reservation reservation;

    public Reservations(BigDecimal amount, BigDecimal totalMoney, Member member, Reservation reservation) {
        super(amount, totalMoney, member);
        this.reservation = reservation;
    }
}
