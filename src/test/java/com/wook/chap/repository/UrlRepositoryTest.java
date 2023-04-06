package com.wook.chap.repository;

import com.wook.chap.ChapApplication;
import com.wook.chap.model.dto.SearchConditionInfo;
import com.wook.chap.model.dto.UrlInfo;
import com.wook.chap.model.entity.Member;
import com.wook.chap.model.entity.Url;
import com.wook.chap.utils.UrlConverter;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Transactional
@Slf4j
@Import(UrlConverter.class)
class UrlRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    UrlRepository urlRepository;

    @Autowired
    UrlConverter urlConverter;

    Long memberId;

    Long url1Id;
    Long url2Id;
    Long url3Id;
    Long url4Id;
    Long url5Id;






    @BeforeEach
    void init() {

        Member member = Member.builder()
                .name("김강욱")
                .email("ktf1686@naver.com")
                .nickname("KIMKANGWOOK")
                .password("01234")
                .build();

        em.persist(member);
        em.flush();


        Url url1 = Url.builder()
                .originalURL("https://www.naver.com/")
                .shortURL(urlConverter.longToShort())
                .member(member)
                .clickCount(0)
                .build();


        Url url2 = Url.builder()
                .originalURL("https://www.google.co.kr")
                .shortURL(urlConverter.longToShort())
                .member(member)
                .clickCount(0)
                .build();



        Url url3 = Url.builder()
                .originalURL("https://www.kakaocorp.com/page/")
                .shortURL(urlConverter.longToShort())
                .member(member)
                .clickCount(0)
                .build();

        Url url4 = Url.builder()
                .originalURL("https://www.daum.net/")
                .shortURL(urlConverter.longToShort())
                .member(member)
                .clickCount(0)
                .build();

        Url url5 = Url.builder()
                .originalURL("https://support.apple.com/ko-kr/HT207122")
                .shortURL(urlConverter.longToShort())
                .member(member)
                .clickCount(0)
                .build();

        String input1 = "2023-03-16 21:07:12";
        String input2 = "2023-04-16 21:07:12";
        String input3 = "2023-05-16 21:07:12";
        String input4 = "2024-05-16 21:07:12";
        String input5 = "2025-05-16 21:07:12";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime1 = LocalDateTime.parse(input1, formatter);
        LocalDateTime localDateTime2 = LocalDateTime.parse(input2, formatter);
        LocalDateTime localDateTime3 = LocalDateTime.parse(input3, formatter);
        LocalDateTime localDateTime4 = LocalDateTime.parse(input4, formatter);
        LocalDateTime localDateTime5 = LocalDateTime.parse(input5, formatter);


        url1.setCreateTime(localDateTime1);
        url2.setCreateTime(localDateTime2);
        url3.setCreateTime(localDateTime3);
        url4.setCreateTime(localDateTime4);
        url5.setCreateTime(localDateTime5);

        em.persist(url1);
        em.persist(url2);
        em.persist(url3);
        em.persist(url4);
        em.persist(url5);
        em.flush();

        this.memberId = member.getId();
        this.url1Id = url1.getId();
        this.url2Id = url2.getId();
        this.url3Id = url3.getId();
        this.url4Id = url4.getId();
        this.url5Id = url5.getId();
    }

    @Test
    void getPagingTest() {
        Url url1 = em.find(Url.class, url1Id);
        Url url2 = em.find(Url.class, url2Id);
        Url url3 = em.find(Url.class, url3Id);
        Url url4 = em.find(Url.class, url4Id);
        Url url5 = em.find(Url.class, url5Id);

        log.debug("createdTime : "+url2.getCreateTime().toString());
        log.debug("createdTime : "+url4.getCreateTime().toString());

        SearchConditionInfo info = new SearchConditionInfo("com",url2.getCreateTime().toLocalDate().toString(),url4.getCreateTime().toLocalDate().toString());
        Pageable pageable = PageRequest.of(0,3, Sort.by("createdTime").ascending());


        Page<UrlInfo> urlInfoPage = urlRepository.searchPage(memberId, info, pageable);
        assertThat(urlInfoPage.getTotalElements()).isEqualTo(1);

        List<UrlInfo> content = urlInfoPage.getContent();
        assertThat(content.stream().findFirst().orElseThrow().getOriginalUrl()).isEqualTo(url3.getOriginalURL());


    }


}