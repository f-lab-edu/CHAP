package com.wook.chap.domain.repository;

import com.wook.chap.entity.Member;
import com.wook.chap.entity.MemberAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface MemberAuthorityRepository extends JpaRepository<MemberRepository, Long> {

    List<MemberAuthority> findByMember(Member member);

}
