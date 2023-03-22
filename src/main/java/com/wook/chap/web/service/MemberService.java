package com.wook.chap.web.service;

import com.wook.chap.domain.repository.MemberAuthorityRepository;
import com.wook.chap.domain.repository.MemberRepository;
import com.wook.chap.entity.Member;
import com.wook.chap.entity.MemberAuthority;
import com.wook.chap.enums.Authority;
import com.wook.chap.web.form.SignUpForm;
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
    public Member signUp(SignUpForm signUpForm) {
        memberRepository.findByName(signUpForm.getMembername())
                .ifPresent(member -> {
                    throw new RuntimeException("이미 가입된 아이디가 존재합니다");
                });

        Member member = Member.builder()
                .name(signUpForm.getMembername())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .email(signUpForm.getEmail())
                .nickname(signUpForm.getNickname())
                .build();

        memberRepository.save(member);

        MemberAuthority memberAuthority = new MemberAuthority(member, Authority.USER);
        memberAuthorityRepository.save(memberAuthority);

        return member;

    }
}
