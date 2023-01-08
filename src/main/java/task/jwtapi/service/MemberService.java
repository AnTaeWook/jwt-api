package task.jwtapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.jwtapi.domain.Member;
import task.jwtapi.dto.MemberJoinRequestDto;
import task.jwtapi.dto.TokenInfo;
import task.jwtapi.jwt.JwtTokenProvider;
import task.jwtapi.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member create(MemberJoinRequestDto memberJoinRequestDto) {

        Member member = Member
                .builder()
                .memberId(memberJoinRequestDto.getMemberId())
                .password(passwordEncoder.encode(memberJoinRequestDto.getPassword()))
                .role(memberJoinRequestDto.getRole())
                .build();
        return memberRepository.save(member);
    }

    @Transactional
    public TokenInfo login(String memberId, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return jwtTokenProvider.generateToken(authentication);
    }

}
