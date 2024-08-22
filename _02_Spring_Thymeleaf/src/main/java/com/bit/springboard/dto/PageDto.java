package com.bit.springboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDto {
    private int startPage;
    private int endPage;
    private boolean prev, next;
    private int total;
    private Criteria cri;

    public PageDto(Criteria cri, int total) {
        this.cri = cri;
        this.total = total;

        this.endPage = (int)(Math.ceil(cri.getPageNum() / 10.0)) * 10;
        this.startPage = this.endPage - 9;

        int realEndPage = (int)(Math.ceil((total / 1.0) / cri.getAmount()));

        if(endPage > realEndPage) {
            this.endPage = realEndPage;
        }

        this.prev = this.cri.getPageNum() > 1;
        this.next = this.cri.getPageNum() < this.endPage;
    }
}
