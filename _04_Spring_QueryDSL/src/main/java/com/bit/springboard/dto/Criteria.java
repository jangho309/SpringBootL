package com.bit.springboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Criteria {
    private int pageNum;
    private int amount;
    private int startNum;

    public Criteria() {
        this(1, 10);
    }

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }
}
