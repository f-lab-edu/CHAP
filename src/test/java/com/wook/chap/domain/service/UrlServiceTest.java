package com.wook.chap.domain.service;

import com.wook.chap.domain.repository.UrlRepository;
import com.wook.chap.entity.Member;
import com.wook.chap.entity.Url;
import io.jsonwebtoken.lang.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UrlServiceTest {

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlRepository urlRepository;

    @Test
    public void convertUrlAndSave()  {
        //given
        String requestUrl = "https://www.freecodecamp.org/korean/news/java-string-to-int-how-to-convert-a-string-to-an-integer/";
        String shortsUrl = urlService.convertUrlAndSave
                (requestUrl, 1L);

        //when
        Url url = urlRepository.findByShortsUrl(shortsUrl).orElseThrow();

        //then
        Assertions.assertThat(url.getOriginalURL()).isEqualTo(requestUrl);
     }
}