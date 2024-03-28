package org.noint.gathering.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UQ_room_date_time", columnNames = {"room_id","date", "time_slot_id"})
})
public class RoomSchedule extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_schedule_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AbleStatus isAble;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private Room room;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "time_slot_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private TimeSlot timeSlot;

    public RoomSchedule(LocalDate date, Room room, TimeSlot timeSlot) {
        this.date = date;
        this.isAble = AbleStatus.ENABLED;
        this.room = room;
        this.timeSlot = timeSlot;
    }
}
