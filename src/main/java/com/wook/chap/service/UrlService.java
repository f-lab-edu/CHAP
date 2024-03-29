package com.wook.chap.service;

import com.wook.chap.model.dto.SearchConditionInfo;
import com.wook.chap.model.dto.UrlInfo;
import com.wook.chap.model.dto.PageResultDto;
import com.wook.chap.model.dto.SearchConditionDto;
import com.wook.chap.model.entity.Member;
import com.wook.chap.model.entity.Url;
import com.wook.chap.exception.NotFoundUrlException;
import com.wook.chap.repository.MemberRepository;
import com.wook.chap.repository.UrlRepository;
import com.wook.chap.utils.UrlConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
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
                .clickCount(0)
                .build();

        urlRepository.save(url);
        return shortUrl;
    }

    public String returnOriginalUrl(String shortsUrl) {
        try {
            Url url = urlRepository.findByShortsUrl(shortsUrl).orElseThrow();
            url.plusClickCount();
            return url.getOriginalURL();
        } catch (NoSuchElementException ne) {
            log.debug("NotFoundUrlException 발생");
            throw new NotFoundUrlException("해당 URL을 찾지 못하였습니다.");
        }

    }

    public PageResultDto<UrlInfo> pagingMyUrl(Long memberId, SearchConditionInfo searchCondition, Pageable pageable) {
        Page<UrlInfo> urlInfoPage = urlRepository.searchPage(memberId, searchCondition, pageable);


        return new PageResultDto<>(urlInfoPage);
    }

    public boolean isMyUrl(Long loginMemberId,Long urlId) {
        Url url = urlRepository.isMyUrl(loginMemberId, urlId).orElse(null);
        if (url==null) return false;
        urlRepository.delete(url);
        return true;
    }

    public void deleteUrlById(Long urlId) {
        urlRepository.deleteById(urlId);
    }
}
