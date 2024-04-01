package org.noint.gathering.domain.reservation.repository;

import org.junit.jupiter.api.Test;
import org.noint.gathering.domain.gathering.service.gathering.GatheringQueryService;
import org.noint.gathering.domain.reservation.dto.response.RoomScheduleResDto;
import org.noint.gathering.domain.reservation.service.ReservationQueryService;
import org.noint.gathering.entity.Gathering;
import org.noint.gathering.entity.Reservation;
import org.noint.gathering.entity.RoomSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReservationQueryRepositoryTest {

    @Autowired ReservationQueryRepository reservationQueryRepository;

    @Autowired GatheringQueryService gatheringQueryService;

    @Autowired ReservationQueryService reservationQueryService;

    @Autowired ReservationRepository reservationRepository;

    @Test
    void 방_스케줄_목록() throws Exception {
        //given
        LocalDate date = LocalDate.now().plusDays(1);

        //when
        List<RoomScheduleResDto> roomSchedule = reservationQueryRepository.findRoomSchedule(date);

        //then
        assertThat(roomSchedule.size()).isEqualTo(70);
    }

    @Test
    void 예약_목록_조회_By_모임_OR_룸_스케줄() throws Exception {
        //given
        Long gatheringId = 1L;
        String requestId = UUID.randomUUID().toString();
        List<Long> roomScheduleIds = new ArrayList<>() {{
            add(1L);
            add(2L);
            add(3L);
            add(4L);
            add(5L);
        }};
        Gathering gathering = gatheringQueryService.getGathering(gatheringId);
        List<RoomSchedule> roomSchedules = reservationQueryService.getRoomSchedules(roomScheduleIds);

        reservationRepository.save(new Reservation(requestId, gathering, roomSchedules.getFirst()));

        //when
        List<Reservation> reservations = reservationQueryService.getAllByGatheringOrRoomSchedules(gathering, roomSchedules);

        //then
        assertThat(reservations.size()).isEqualTo(1);
    }
}