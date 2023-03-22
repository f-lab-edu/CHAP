package com.wook.chap.domain.repository;

import com.wook.chap.entity.Member;
import com.wook.chap.entity.MemberAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAuthorityRepository extends JpaRepository<MemberAuthority, Long> {

    List<MemberAuthority> findByMember(Member member);

}
