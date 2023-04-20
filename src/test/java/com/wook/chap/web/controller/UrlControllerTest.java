package com.wook.chap.web.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;


@SpringBootTest
class UrlControllerTest {

    private static String pattern = "^[a-z0-9A-Z]{6}$";

    @Test
    public void patternMatchTest()  {
        String url1 = "abcdef";
        String url2 = "A8czEF";
        String url3 = "AS5D62Ssa";
        String url4 = "12358888";

        Assertions.assertThat(Pattern.matches(pattern, url1)).isTrue();
        Assertions.assertThat(Pattern.matches(pattern, url2)).isTrue();
        Assertions.assertThat(Pattern.matches(pattern, url3)).isFalse();
        Assertions.assertThat(Pattern.matches(pattern, url4)).isFalse();


     }
}