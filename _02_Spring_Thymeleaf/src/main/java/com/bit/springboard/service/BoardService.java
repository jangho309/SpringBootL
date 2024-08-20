package com.bit.springboard.service;

import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.BoardFileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    BoardDto post(BoardDto boardDto, MultipartFile[] uploadFiles);

    List<BoardDto> findAll();

    BoardDto findById(int id);

    List<BoardFileDto> findFilesById(int id);

    BoardDto modify(BoardDto boardDto, MultipartFile[] uploadFiles, MultipartFile[] changeFiles, String originFiles);
}
