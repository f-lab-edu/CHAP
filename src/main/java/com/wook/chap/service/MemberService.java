package com.wook.chap.service;

import com.wook.chap.model.entity.Member;
import com.wook.chap.model.entity.MemberAuthority;
import com.wook.chap.model.enums.Authority;
import com.wook.chap.model.dto.SignUpDto;
import com.wook.chap.repository.MemberAuthorityRepository;
import com.wook.chap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final MemberAuthorityRepository memberAuthorityRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member signUp(SignUpDto signUpDto) {
        memberRepository.findByName(signUpDto.getMembername())
                .ifPresent(member -> {
                    throw new RuntimeException("이미 가입된 아이디가 존재합니다");
                });

        Member member = Member.builder()
                .name(signUpDto.getMembername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .email(signUpDto.getEmail())
                .nickname(signUpDto.getNickname())
                .build();

        memberRepository.save(member);

        MemberAuthority memberAuthority = new MemberAuthority(member, Authority.USER);
        memberAuthorityRepository.save(memberAuthority);

        return member;

    }
}
