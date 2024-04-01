package org.noint.gathering.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static org.noint.gathering.entity.Progress.PROGRESS;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(indexes = {
        @Index(name = "IX_gather_id", columnList = "gathering_id"),
}, uniqueConstraints = {
        @UniqueConstraint(name = "UQ_reservation_schedule", columnNames = {"request_id", "room_schedule_id"})
})
public class Reservation extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Progress progress;

    @Column(nullable = false, length = 36)
    private String requestId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gathering_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private Gathering gathering;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_schedule_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private RoomSchedule roomSchedule;

    public Reservation(String requestId, Gathering gathering, RoomSchedule roomSchedule) {
        this.progress = PROGRESS;
        this.requestId = requestId;
        this.gathering = gathering;
        this.roomSchedule = roomSchedule;
    }

    public void updateProgress(Progress progress) {
        this.progress = progress;
    }
}
