package com.bit.springboard.mapper;

import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.BoardFileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FreeMapper {
    void post(BoardDto boardDto);

    BoardDto findById(Long id);

    void postFiles(List<BoardFileDto> boardFileDtoList);

    List<BoardDto> findAll(Map<String, Object> paramMap);

    List<BoardFileDto> findFilesById(Long id);

    void modify(BoardDto boardDto);

    void modifyFile(BoardFileDto boardFileDto);

    void removeFile(BoardFileDto boardFileDto);

    void postFile(BoardFileDto boardFileDto);

    void updateBoardCnt(Long id);

    void removeFiles(Long id);

    void remove(Long id);

    int findTotalCnt(Map<String, String> searchMap);
}
