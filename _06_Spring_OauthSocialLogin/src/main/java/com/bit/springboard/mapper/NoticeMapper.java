package com.bit.springboard.mapper;

import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.BoardFileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeMapper {
    void post(BoardDto boardDto);

    void postFiles(List<BoardFileDto> boardFileDtoList);

    BoardDto findById(Long id);

    List<BoardDto> findAll(Map<String, Object> paramMap);

    List<BoardFileDto> findFilesById(Long id);

    void updateBoardCnt(Long id);

    void modify(BoardDto boardDto);

    void modifyFile(BoardFileDto boardFileDto);

    void removeFile(BoardFileDto boardFileDto);

    void postFile(BoardFileDto boardFileDto);

    int findTotalCnt(Map<String, String> searchMap);

    void removeFiles(Long id);

    void remove(Long id);
}
