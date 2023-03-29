package com.wook.chap.domain.service;

import com.wook.chap.domain.repository.MemberRepository;
import com.wook.chap.domain.repository.UrlRepository;
import com.wook.chap.entity.Member;
import com.wook.chap.entity.Url;
import com.wook.chap.web.utils.UrlConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UrlService {

    private final UrlConverter urlConverter;

    private final MemberRepository memberRepository;

    private final UrlRepository urlRepository;

    public String convertUrlAndSave(String requestUrl,Long memberId) {
        String shortUrl = urlConverter.longToShort();
        Member member = memberRepository.findById(memberId).get();

        Url url = Url.builder()
                .member(member)
                .shortURL(shortUrl)
                .originalURL(requestUrl)
                .clickCnt(0)
                .build();

        urlRepository.save(url);
        return shortUrl;
    }

}
