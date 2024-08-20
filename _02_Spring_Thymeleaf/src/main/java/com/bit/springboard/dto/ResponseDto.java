package com.bit.springboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseDto<T> {
    private T data;
    private List<T> dataList;
    private int statusCode;
    private String statusMessage;
}
