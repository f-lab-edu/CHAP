package com.wook.chap.repository;

import com.wook.chap.model.entity.Member;
import com.wook.chap.model.entity.MemberAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAuthorityRepository extends JpaRepository<MemberAuthority, Long> {

    List<MemberAuthority> findByMember(Member member);

}
