package org.noint.gathering.domain.gathering.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.gathering.dto.request.GatheringReqDto;
import org.noint.gathering.domain.gathering.dto.request.CommentBodyReqDto;
import org.noint.gathering.domain.gathering.dto.response.CommentsResDto;
import org.noint.gathering.domain.gathering.dto.response.GatheringInfoResDto;
import org.noint.gathering.domain.gathering.dto.response.ParticipantsResDto;
import org.noint.gathering.domain.gathering.service.comment.CommentCommendService;
import org.noint.gathering.domain.gathering.service.comment.CommentQueryService;
import org.noint.gathering.domain.gathering.service.gathering.GatheringCommendService;
import org.noint.gathering.domain.gathering.service.participant.ParticipantQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GatheringController {

    private final GatheringCommendService gatheringCommendService;

    private final ParticipantQueryService participantQueryService;

    private final CommentCommendService commentCommendService;

    private final CommentQueryService commentQueryService;

    @PostMapping("/gathering")
    public ResponseEntity<GatheringInfoResDto> createGathering(@RequestAttribute("memberId") Long memberId,
                                                               @Valid @RequestBody GatheringReqDto request) {
        log.info("모임 개설 API");
        return new ResponseEntity<>(gatheringCommendService.createGathering(memberId, request), HttpStatus.CREATED);
    }

    @PostMapping("/gathering/{gatheringId}")
    public ResponseEntity<GatheringInfoResDto> entryGathering(@RequestAttribute("memberId") Long memberId,
                                                              @PathVariable("gatheringId") Long gatheringId) {
        log.info("모임 참가 API");
        gatheringCommendService.entryGathering(memberId, gatheringId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/participant/{gatheringId}")
    public ResponseEntity<Page<ParticipantsResDto>> getParticipants(@PathVariable("gatheringId") Long gatheringId,
                                                                    Pageable pageable) {
        log.info("모임 참가자 조회 API");
        return new ResponseEntity<>(participantQueryService.getParticipants(gatheringId, pageable), HttpStatus.OK);
    }

    @PostMapping("/comment/{gatheringId}")
    public ResponseEntity<Void> writeComment(@RequestAttribute("memberId") Long memberId,
                                             @PathVariable("gatheringId") Long gatheringId,
                                             @Valid @RequestBody CommentBodyReqDto request) {
        commentCommendService.writeComment(memberId, gatheringId, request.body());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/comment/{gatheringId}")
    public ResponseEntity<Slice<CommentsResDto>> getComments(@RequestAttribute("memberId") Long memberId,
                                                             @PathVariable("gatheringId") Long gatheringId,
                                                             Pageable pageable) {
        return new ResponseEntity<>(commentQueryService.getComments(memberId, gatheringId, pageable), HttpStatus.OK);
    }
}
