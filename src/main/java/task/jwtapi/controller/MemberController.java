package task.jwtapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import task.jwtapi.domain.Member;
import task.jwtapi.domain.Role;
import task.jwtapi.dto.MemberJoinRequestDto;
import task.jwtapi.dto.MemberLoginRequestDto;
import task.jwtapi.dto.TokenInfo;
import task.jwtapi.repository.MemberRepository;
import task.jwtapi.service.MemberService;
import task.jwtapi.util.SecurityUtil;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/join")
    public Member join(@RequestBody MemberJoinRequestDto memberJoinRequestDto) {
        return memberService.create(memberJoinRequestDto);
    }

    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String memberId = memberLoginRequestDto.getMemberId();
        String password = memberLoginRequestDto.getPassword();
        return memberService.login(memberId, password);
    }

    @GetMapping("/test")
    public String test() {
        Member member = memberRepository.findByMemberId(SecurityUtil.getCurrentMemberId()).get();
        if (member.getRole().equals(Role.ADMIN)) {
            return "관리자님 환영합니다";
        } else if (member.getRole().equals(Role.USER)) {
            return "주주님 환영합니다";
        }
        return "어케 들어왔누?";
    }
}
