package org.noint.gathering.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomSchedule extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "room_schedule_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private AbleStatus isAble;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "time_slot_id")
    private TimeSlot timeSlot;

    public RoomSchedule(Room room, TimeSlot timeSlot) {
        this.isAble = AbleStatus.ENABLED;
        this.room = room;
        this.timeSlot = timeSlot;
    }
}
