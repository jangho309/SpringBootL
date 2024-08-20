package com.bit.springboard.service.impl;

import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.BoardFileDto;
import com.bit.springboard.service.BoardService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class NoticeServiceImpl implements BoardService {
    @Override
    public BoardDto post(BoardDto boardDto, MultipartFile[] uploadFiles) {
        return null;
    }

    @Override
    public List<BoardDto> findAll() {
        return List.of();
    }

    @Override
    public BoardDto findById(int id) {
        return null;
    }

    @Override
    public List<BoardFileDto> findFilesById(int id) {
        return List.of();
    }

    @Override
    public BoardDto modify(BoardDto boardDto, MultipartFile[] uploadFiles, MultipartFile[] changeFiles, String originFiles) {
        return null;
    }
}
