package com.wook.chap;

import com.wook.chap.model.entity.Member;
import com.wook.chap.model.entity.MemberAuthority;
import com.wook.chap.model.enums.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Profile("local")
@RequiredArgsConstructor
@Component
public class InitData {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }


    @Component
    static class InitService {

        @PersistenceContext
        private EntityManager em;


        @Transactional
        public void init() {
            // 비번 qwer1234!
            Member member1 = new Member("ktf1686", "rlarkddnr1686@naver.com","$2a$10$1ZenE74j0gH/2bIbACBVhuVl9xFTy8dxK25I8Fi4nLd/I0puS9i4y","강욱");
            Member member2 = new Member("skt2684", "rlarkddnr1686@daum.net","$2a$10$1ZenE74j0gH/2bIbACBVhuVl9xFTy8dxK25I8Fi4nLd/I0puS9i4y","욱강");

            em.persist(member1);
            em.persist(member2);

            MemberAuthority authority1 = new MemberAuthority(member1, Authority.MANAGER);
            MemberAuthority authority2 = new MemberAuthority(member2, Authority.USER);

            em.persist(authority1);
            em.persist(authority2);

            em.flush();



        }

    }




}
