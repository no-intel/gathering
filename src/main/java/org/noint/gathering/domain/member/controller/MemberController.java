package org.noint.gathering.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.member.dto.request.RegisterReqDto;
import org.noint.gathering.domain.member.service.MemberCommendService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberCommendService memberCommendService;

    @PostMapping("/member")
    public ResponseEntity<Long> register(@Valid @RequestBody RegisterReqDto request) {
        log.info("회원 가입 API");
        Long newMemberId = memberCommendService.register(request);
        return new ResponseEntity<>(newMemberId, HttpStatus.CREATED);
    }
}
