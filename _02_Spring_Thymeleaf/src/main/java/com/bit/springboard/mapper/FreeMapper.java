package com.bit.springboard.mapper;

import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.BoardFileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FreeMapper {
    void post(BoardDto boardDto);

    BoardDto findById(int id);

    void postFiles(List<BoardFileDto> boardFileDtoList);

    List<BoardDto> findAll(Map<String, Object> paramMap);

    List<BoardFileDto> findFilesById(int id);

    void modify(BoardDto boardDto);

    void modifyFile(BoardFileDto boardFileDto);

    void removeFile(BoardFileDto boardFileDto);

    void postFile(BoardFileDto boardFileDto);

    void updateBoardCnt(int id);

    void removeFiles(int id);

    void remove(int id);

    int findTotalCnt(Map<String, String> searchMap);
}
