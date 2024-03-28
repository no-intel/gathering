package org.noint.gathering.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private int capacity;

    @Column(precision = 6, scale = 0, nullable = false)
    private BigDecimal timeRate;

    public Room(String name, int capacity, BigDecimal timeRate) {
        this.name = name;
        this.capacity = capacity;
        this.timeRate = timeRate;
    }
}
