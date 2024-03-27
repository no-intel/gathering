package org.noint.gathering.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    private String requestId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gathering_id")
    private Gathering gathering;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_schedule_id")
    private RoomSchedule roomSchedule;

    public Reservation(String requestId, Gathering gathering, RoomSchedule roomSchedule) {
        this.requestId = requestId;
        this.gathering = gathering;
        this.roomSchedule = roomSchedule;
    }
}
