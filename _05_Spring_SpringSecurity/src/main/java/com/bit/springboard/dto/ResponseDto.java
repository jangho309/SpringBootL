package com.bit.springboard.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class ResponseDto<T> {
    private T data;
    private List<T> dataList;
    private Page<T> dataPaging;
    private int statusCode;
    private String statusMessage;
}
