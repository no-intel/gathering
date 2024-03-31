package org.noint.gathering.domain.reservation.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import org.noint.gathering.entity.AbleStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RoomScheduleResDto(
        Long roomScheduleId,
        Long roomId,
        String roomName,
        int capacity,
        BigDecimal timeRate,
        Long timeSlotId,
        String time,
        LocalDate date,
        AbleStatus isAble
) {
    @QueryProjection
    public RoomScheduleResDto(Long roomScheduleId, Long roomId, String roomName, int capacity, BigDecimal timeRate, Long timeSlotId, String time, LocalDate date, AbleStatus isAble) {
        this.roomScheduleId = roomScheduleId;
        this.roomId = roomId;
        this.roomName = roomName;
        this.capacity = capacity;
        this.timeRate = timeRate;
        this.timeSlotId = timeSlotId;
        this.time = time;
        this.date = date;
        this.isAble = isAble;
    }
}
