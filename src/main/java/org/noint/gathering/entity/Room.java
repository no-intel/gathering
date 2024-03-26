package org.noint.gathering.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    private String name;

    private int capacity;

    private BigDecimal hourlyRate;

    public Room(String name, int capacity, BigDecimal hourlyRate) {
        this.name = name;
        this.capacity = capacity;
        this.hourlyRate = hourlyRate;
    }
}
