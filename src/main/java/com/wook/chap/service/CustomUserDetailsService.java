package com.wook.chap.service;

import com.wook.chap.domain.repository.MemberAuthorityRepository;
import com.wook.chap.domain.repository.MemberRepository;
import com.wook.chap.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final MemberAuthorityRepository memberAuthorityRepository;

    @Override
    public UserDetails loadUserByUsername(String memberName) throws UsernameNotFoundException {
        return memberRepository.findByName(memberName)
                .map(member -> createUser(memberName, member))
                .orElseThrow(() -> {
                    log.debug("DB에 {} 이 없습니다.",memberName);
                    return new UsernameNotFoundException(memberName + " -> 데이터베이스에서 찾을 수 없습니다.");
                });
    }

    private User createUser(String memberName, Member member) {
        log.debug("member 정보 : {}", member);

        List<SimpleGrantedAuthority> grantedAuthorities = memberAuthorityRepository.findByMember(member).stream()
                .map(memberAuthority -> new SimpleGrantedAuthority(memberAuthority.getAuthority().toString()))
                .collect(Collectors.toList());

        return new User(member.getId().toString(),
                member.getPassword(), grantedAuthorities);

    }
}
