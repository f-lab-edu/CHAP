package com.wook.chap.repository;

import com.wook.chap.model.dto.SearchConditionInfo;
import com.wook.chap.model.dto.UrlInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UrlRepositoryCustom {

    Page<UrlInfo> searchPage(Long memberId, SearchConditionInfo searchCondition, Pageable pageable);
}
