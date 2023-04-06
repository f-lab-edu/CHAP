package com.wook.chap;

import com.wook.chap.model.entity.Member;
import com.wook.chap.model.entity.MemberAuthority;
import com.wook.chap.model.entity.Url;
import com.wook.chap.model.enums.Authority;
import com.wook.chap.utils.UrlConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

        @Autowired
        private UrlConverter urlConverter;


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


            Url url1 = Url.builder()
                    .originalURL("https://kukim.tistory.com/150")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url2 = Url.builder()
                    .originalURL("https://www.youtube.com/watch?v=kSUiMc97dKM&t=2839s")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url3 = Url.builder()
                    .originalURL("https://hyeonseok.com/blog/709")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url4 = Url.builder()
                    .originalURL("https://velog.io/@dev_zzame/Spring-Boot-Devtools-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0for-%ED%8E%B8%ED%95%9C-%EA%B0%9C%EB%B0%9C")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url5 = Url.builder()
                    .originalURL("https://www.naver.com/")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url6 = Url.builder()
                    .originalURL("https://www.google.com/search?q=css+container%EB%9E%80&oq=css+container%EB%9E%80&aqs=chrome..69i57j0i512l9.2451j1j15&sourceid=chrome&ie=UTF-8")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url7 = Url.builder()
                    .originalURL("https://ssd0908.tistory.com/entry/thymeleaf-%ED%83%80%EC%9E%84%EB%A6%AC%ED%94%84-properties%EC%97%90-%EC%A0%95%EC%9D%98%EB%90%9C-%EA%B0%92-%EA%B0%80%EC%A0%B8%EC%98%A4%EA%B8%B0")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url8 = Url.builder()
                    .originalURL("https://www.google.com/search?q=%EB%8D%94%EC%A1%B4%EB%B9%84%EC%A6%88%EC%98%A8&oq=&aqs=chrome.6.35i39i362l8.213322070j0j15&sourceid=chrome&ie=UTF-8")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url9 = Url.builder()
                    .originalURL("https://douzoneon.com//s1/s1_5.php?network=g&device=c&keyword=%EB%8D%94%EC%A1%B4%EB%B9%84%EC%A6%88%EC%98%A8&creative=411268144036&gclid=Cj0KCQjw27mhBhC9ARIsAIFsETFXOK717Q737JKeezzB3WBcuI4M11N5fk9nOn53T2_sW-KRg4SRgvEaAhd3EALw_wcB&campaignid=8726967288&adgroupid=84601925170&matchtype=e&placement=&sourceid={sourceid}&gclid=Cj0KCQjw27mhBhC9ARIsAIFsETFXOK717Q737JKeezzB3WBcuI4M11N5fk9nOn53T2_sW-KRg4SRgvEaAhd3EALw_wcB")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url10 = Url.builder()
                    .originalURL("https://line.me/ko/")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url11 = Url.builder()
                    .originalURL("https://supercoding.net/?utm_campaign=sa&utm_source=google_pc&utm_medium=cpc&utm_term=react&gclid=Cj0KCQjw27mhBhC9ARIsAIFsETHrbpzqDEsKAyB6plzijKof8CTDul54mR5tJUvYcbei3f_n6UTMZ5UaAiJpEALw_wcB")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url12 = Url.builder()
                    .originalURL("https://www.codestates.com/course/uxui?utm_medium=cpc&utm_source=google&utm_campaign=%EA%B5%AC%EA%B8%80SA_UUB_PC%2FMO&utm_term=react&utm_content=02.%EB%94%94%EC%9E%90%EC%9D%B8%2F%EA%B0%9C%EB%B0%9C&gclid=Cj0KCQjw27mhBhC9ARIsAIFsETFKtCJWxysgbFijoDoKF4ccw3uImCvB5Wv9Bv9fyNG_4PPv91GbEBgaAg6dEALw_wcB")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url13 = Url.builder()
                    .originalURL("https://userguiding.com/blog/angular-react-vue/?utm_term=react%20javascript&utm_campaign=South+Korea+Targeted&utm_source=adwords&utm_medium=ppc&hsa_acc=8955685950&hsa_cam=11908900463&hsa_grp=153519793624&hsa_ad=650326096479&hsa_src=g&hsa_tgt=kwd-335211289666&hsa_kw=react%20javascript&hsa_mt=p&hsa_net=adwords&hsa_ver=3&gclid=Cj0KCQjw27mhBhC9ARIsAIFsETEgNFeZF7PvaLv4tv95Zh66SFPio9eMK66FcwS9is4v_IZ4CMFj3OYaAg44EALw_wcB")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url14 = Url.builder()
                    .originalURL("https://ko.reactjs.org/")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url15 = Url.builder()
                    .originalURL("https://namu.wiki/w/React(%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC)")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url16 = Url.builder()
                    .originalURL("https://www.bit.kr/")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url17 = Url.builder()
                    .originalURL("https://www.oracle.com/kr/")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url18 = Url.builder()
                    .originalURL("https://ko.wikipedia.org/wiki/%EC%98%A4%EB%9D%BC%ED%81%B4_(%EA%B8%B0%EC%97%85)")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url19 = Url.builder()
                    .originalURL("https://opentutorials.org/course/3885")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();

            Url url20 = Url.builder()
                    .originalURL("https://www.twitch.tv/")
                    .shortURL(urlConverter.longToShort())
                    .clickCount(0)
                    .member(member1)
                    .build();


            em.persist(url1);
            em.persist(url2);
            em.persist(url3);
            em.persist(url4);
            em.persist(url5);
            em.persist(url6);
            em.persist(url7);
            em.persist(url8);
            em.persist(url9);
            em.persist(url10);
            em.persist(url11);
            em.persist(url12);
            em.persist(url13);
            em.persist(url14);
            em.persist(url15);
            em.persist(url16);
            em.persist(url17);
            em.persist(url18);
            em.persist(url19);
            em.persist(url20);

            em.flush();



        }

    }




}
