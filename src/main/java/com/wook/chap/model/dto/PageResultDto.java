package com.wook.chap.model.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.wook.chap.model.constant.PageConstant.DEFAULT_PAGE_SIZE;


@Data
@Slf4j
public class PageResultDto<DTO> {
    public List<DTO> dtoList; //DTO 리스트

    private int totalPages; //총 페이지 번호

    private int page; //현재 페이지 번호

    private int pageSize; //페이지 크기

    private int size; //페이지 당 엔티티 갯수

    private int start, end; //시작, 끝 페이지

    private boolean first, last; //첫 페이지인지, 마지막 페이지인지 체크

    private boolean prev,next; //이전 페이지, 다음 페이지 있는지 체크

    private String sort;
    private boolean isAsc;

    private List<Integer> pageList;

    private boolean isNotEmpty() {
        return !this.dtoList.isEmpty();
    }



    public PageResultDto(Page<DTO> dtoPage) {
        dtoList = dtoPage.toList();
        totalPages = dtoPage.getTotalPages();
        makePageList(dtoPage.getPageable());
    }

    private void makePageList(Pageable pageable){

        this.page = pageable.getPageNumber() + 1; //0부터 시작하므로 1을 추가
        this.size = pageable.getPageSize();
        this.pageSize = DEFAULT_PAGE_SIZE;

        int tempEnd = (int)(Math.ceil(page/(float)DEFAULT_PAGE_SIZE)) * DEFAULT_PAGE_SIZE;

        start = tempEnd - (DEFAULT_PAGE_SIZE-1);

        prev = start > 1;

        first =(page<=DEFAULT_PAGE_SIZE);

        last =(Math.floor(totalPages/DEFAULT_PAGE_SIZE)*DEFAULT_PAGE_SIZE<=start);

        end = totalPages > tempEnd ? tempEnd : totalPages;

        next = totalPages > tempEnd;

        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());

        sort = pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse(null);

        isAsc = pageable.getSort().stream().findFirst().map((order)-> order.getDirection().isAscending()).orElse(true);
    }
}
