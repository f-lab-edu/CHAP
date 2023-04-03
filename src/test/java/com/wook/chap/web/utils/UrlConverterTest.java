package com.wook.chap.web.utils;

import com.wook.chap.utils.UrlConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UrlConverterTest {

    @Autowired
    private UrlConverter converter;

    @Test
    public void makeShortsUrl()  {

        String shortUrl1 = converter.longToShort();
        String shortUrl2 = converter.longToShort();
        String shortUrl3 = converter.longToShort();
        char char1 = shortUrl1.charAt(5);
        char char2 = shortUrl1.charAt(5);
        char char3 = shortUrl1.charAt(5);

        assertThat(shortUrl1.length()).isEqualTo(6);
        assertThat(shortUrl2.length()).isEqualTo(6);
        assertThat(shortUrl3.length()).isEqualTo(6);
        assertThat(char2-char1).isEqualTo(1);
        assertThat(char3-char1).isEqualTo(2);
        assertThat(char3-char2).isEqualTo(1);


     }

}