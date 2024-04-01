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
        @Index(name = "IX_room_schedule_id", columnList = "room_schedule_id")
})
public class Reservation extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated
    private Progress progress;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gathering_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private Gathering gathering;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_schedule_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private RoomSchedule roomSchedule;

    public Reservation(Gathering gathering, RoomSchedule roomSchedule) {
        this.progress = PROGRESS;
        this.gathering = gathering;
        this.roomSchedule = roomSchedule;
    }
}
