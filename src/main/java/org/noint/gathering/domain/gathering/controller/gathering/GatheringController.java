package org.noint.gathering.domain.gathering.controller.gathering;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.gathering.dto.gathering.request.GatheringReqDto;
import org.noint.gathering.domain.gathering.dto.gathering.response.GatheringInfoResDto;
import org.noint.gathering.domain.gathering.service.gathering.GatheringCommendService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GatheringController {

    private final GatheringCommendService gatheringCommendService;

    @PostMapping("/gathering")
    public ResponseEntity<GatheringInfoResDto> createGathering(@RequestAttribute("memberId") Long memberId,
                                                               @Valid @RequestBody GatheringReqDto request) {
        log.info("모임 개설 API");
        return new ResponseEntity<>(gatheringCommendService.createGathering(memberId, request), HttpStatus.CREATED);
    }
}
