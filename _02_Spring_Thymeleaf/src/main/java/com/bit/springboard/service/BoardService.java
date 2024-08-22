package com.bit.springboard.service;

import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.BoardFileDto;
import com.bit.springboard.dto.Criteria;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BoardService {
    BoardDto post(BoardDto boardDto, MultipartFile[] uploadFiles);

    List<BoardDto> findAll(Map<String, String> searchMap, Criteria cri);

    BoardDto findById(int id);

    List<BoardFileDto> findFilesById(int id);

    BoardDto modify(BoardDto boardDto, MultipartFile[] uploadFiles, MultipartFile[] changeFiles, String originFiles);

    void updateBoardCnt(int id);

    void remove(int id);

    int findTotalCnt(Map<String, String> searchMap);
}
