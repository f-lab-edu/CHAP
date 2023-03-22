package com.wook.chap.entity;

import com.wook.chap.enums.Authority;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MemberAuthority {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public MemberAuthority(Member member, Authority authority) {
        this.member = member;
        this.authority = authority;
    }
}
