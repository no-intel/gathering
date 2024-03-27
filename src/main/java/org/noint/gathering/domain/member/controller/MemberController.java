package org.noint.gathering.domain.member.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.member.dto.request.LoginReqDto;
import org.noint.gathering.domain.member.dto.request.RegisterReqDto;
import org.noint.gathering.domain.member.dto.response.MyInfoResDto;
import org.noint.gathering.domain.member.service.MemberCommendService;
import org.noint.gathering.domain.member.service.MemberQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberCommendService memberCommendService;

    private final MemberQueryService memberQueryService;

    private final HttpSession session;

    @PostMapping("/member")
    public ResponseEntity<Long> register(@Valid @RequestBody RegisterReqDto request) {
        log.info("회원 가입 API");
        Long newMemberId = memberCommendService.register(request);
        return new ResponseEntity<>(newMemberId, HttpStatus.CREATED);
    }

    @PostMapping("/member/login")
    public ResponseEntity<MyInfoResDto> login(@Valid @RequestBody LoginReqDto request) {
        log.info("로그인 API");
        MyInfoResDto myInfo = memberQueryService.login(request);
        session.setAttribute("memberId", myInfo.memberId());
        return new ResponseEntity<>(myInfo, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<MyInfoResDto> myInfo(@RequestAttribute("memberId") Long memberId) {
        log.info("내 정보 API");
        return new ResponseEntity<>(memberQueryService.myInfo(memberId), HttpStatus.OK);
    }

    @PatchMapping("/member/resign")
    public ResponseEntity<Void> resign(@RequestAttribute("memberId") Long memberId) {
        log.info("탈퇴 API");
        memberCommendService.resign(memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
