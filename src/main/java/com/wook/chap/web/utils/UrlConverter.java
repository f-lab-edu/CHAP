package com.wook.chap.web.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class UrlConverter {


    static AtomicLong COUNTER = new AtomicLong(10000000000L);

    static String elements = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";


    public String longToShort() {
        String shorturl = base10ToBase62(COUNTER.getAndIncrement());
        return  shorturl;
    }

    private String base10ToBase62(long n) {
        StringBuilder sb = new StringBuilder();
        while (n != 0) {
            sb.insert(0, elements.charAt((int) n % 62));
            n /= 62;
        }

        return sb.toString();
    }
}
