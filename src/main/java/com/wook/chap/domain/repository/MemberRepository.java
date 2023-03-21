package com.wook.chap.domain.repository;

import com.wook.chap.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByMemberName(String memberName);
}
