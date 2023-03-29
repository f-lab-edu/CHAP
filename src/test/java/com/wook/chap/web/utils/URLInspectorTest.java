package com.wook.chap.web.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class URLInspectorTest {

    @Autowired
    private URLInspector urlInspector;


    @Test
    public void urlInspection() {
        //given
        String requestURI1 = "https://www.google.com/search?q=URI+%EA%B2%80%EC%82%AC%EC%A4%91&oq=URI+%EA%B2%80%EC%82%AC%EC%A4%91&aqs=chrome..69i57j33i10i160.1875j0j15&sourceid=chrome&ie=UTF-8";
        String requestURI2 = "https://docs.oracle.com/cd/E19050-01/sun.cluster31/819-2109/auto20/index.html";
        String requestURI3 = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=URI+%EA%B2%80%EC%82%AC%EC%A4%91";
        String requestURI4 = "https://www.dqwdwqqwdw.com";



        //then
        Assertions.assertThat(urlInspector.urlValidator(requestURI1)).isTrue();
        Assertions.assertThat(urlInspector.urlValidator(requestURI2)).isTrue();
        Assertions.assertThat(urlInspector.urlValidator(requestURI3)).isTrue();
        Assertions.assertThat(urlInspector.urlValidator(requestURI4)).isFalse();


    }

}