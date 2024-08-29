package com.bit.springboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticePageDto {
    private int endPage;
    private Criteria cri;
    private int total;

    public NoticePageDto(Criteria cri, int total) {
        this.cri = cri;
        this.total = total;

        this.endPage = (int)(Math.ceil(total / 1.0) / cri.getAmount());
    }
}
