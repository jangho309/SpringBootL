package com.bit.springboard.service.impl;

import com.bit.springboard.common.FileUtils;
import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.BoardFileDto;
import com.bit.springboard.dto.Criteria;
import com.bit.springboard.mapper.NoticeMapper;
import com.bit.springboard.service.BoardService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements BoardService {
    private final NoticeMapper noticeMapper;
    private final FileUtils fileUtils;

//    public NoticeServiceImpl(NoticeMapper noticeMapper, FileUtils fileUtils) {
//        this.noticeMapper = noticeMapper;
//        this.fileUtils = fileUtils;
//    }

    @Override
    public BoardDto post(BoardDto boardDto, MultipartFile[] uploadFiles) {
        List<BoardFileDto> boardFileDtoList = new ArrayList<>();

        if(uploadFiles != null && uploadFiles.length > 0) {
            Arrays.stream(uploadFiles).forEach(file -> {
                if(!file.getOriginalFilename().equals("") && file.getOriginalFilename() != null) {
                    BoardFileDto boardFileDto = fileUtils.parserFileInfo(file, "notice/");

                    boardFileDtoList.add(boardFileDto);
                }
            });
        }

        noticeMapper.post(boardDto);

        if(boardFileDtoList.size() > 0) {
            boardFileDtoList.forEach(boardFileDto -> boardFileDto.setBoard_id(boardDto.getId()));
            noticeMapper.postFiles(boardFileDtoList);
        }

        return noticeMapper.findById(boardDto.getId());
    }

    @Override
    public List<BoardDto> findAll(Map<String, String> searchMap, Criteria cri) {
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("search", searchMap);

        cri.setStartNum((cri.getPageNum() - 1) * cri.getAmount());

        paramMap.put("cri", cri);

        return noticeMapper.findAll(paramMap);
    }

    @Override
    public BoardDto findById(int id) {
        return noticeMapper.findById(id);
    }

    @Override
    public List<BoardFileDto> findFilesById(int id) {
        return noticeMapper.findFilesById(id);
    }

    @Override
    public BoardDto modify(BoardDto boardDto, MultipartFile[] uploadFiles, MultipartFile[] changeFiles, String originFiles) {
        List<BoardFileDto> originFilesList = new ArrayList<>();
        try {
            originFilesList = new ObjectMapper().readValue(
                    originFiles,
                    new TypeReference<List<BoardFileDto>>() {}
            );
        } catch(IOException ie){
            System.out.println(ie.getMessage());
        }

        List<BoardFileDto> uFilesList = new ArrayList<>();

        if(originFilesList.size() > 0){
            originFilesList.forEach(boardFileDto -> {
                if(boardFileDto.getFilestatus().equals("U") && changeFiles != null){
                    Arrays.stream(changeFiles).forEach(file -> {
                        if(boardFileDto.getNewfilename().equals(file.getOriginalFilename())){
                            BoardFileDto updatedBoardFileDto = fileUtils.parserFileInfo(file, "notice/");

                            updatedBoardFileDto.setBoard_id(boardDto.getId());
                            updatedBoardFileDto.setId(boardFileDto.getId());
                            updatedBoardFileDto.setFilestatus("U");

                            uFilesList.add(updatedBoardFileDto);
                        }
                    });
                } else if(boardFileDto.getFilestatus().equals("D")){
                    BoardFileDto deleteBoardFileDto = new BoardFileDto();

                    deleteBoardFileDto.setBoard_id(boardDto.getId());
                    deleteBoardFileDto.setId(boardFileDto.getId());
                    deleteBoardFileDto.setFilestatus("D");

                    fileUtils.deleteFile("notice/", boardFileDto.getFilename());

                    uFilesList.add(deleteBoardFileDto);
                }
            });
        }

        if(uploadFiles != null && uploadFiles.length > 0){
            Arrays.stream(uploadFiles).forEach(file -> {
                if(file.getOriginalFilename() != null && !file.getOriginalFilename().equals("")) {
                    BoardFileDto postBoardFileDto = fileUtils.parserFileInfo(file, "notice/");

                    postBoardFileDto.setBoard_id(boardDto.getId());
                    postBoardFileDto.setFilestatus("I");

                    uFilesList.add(postBoardFileDto);
                }
            });
        }

        boardDto.setModdate(LocalDateTime.now());
        noticeMapper.modify(boardDto);

        uFilesList.forEach(boardFileDto -> {
            if(boardFileDto.getFilestatus().equals("U")){
                noticeMapper.modifyFile(boardFileDto);
            } else if(boardFileDto.getFilestatus().equals("D")){
                noticeMapper.removeFile(boardFileDto);
            } else if(boardFileDto.getFilestatus().equals("I")){
                noticeMapper.postFile(boardFileDto);
            }
        });

        return noticeMapper.findById(boardDto.getId());
    }

    @Override
    public void updateBoardCnt(int id) {
        noticeMapper.updateBoardCnt(id);
    }

    @Override
    public void remove(int id) {
        noticeMapper.removeFiles(id);
        noticeMapper.remove(id);
    }

    @Override
    public int findTotalCnt(Map<String, String> searchMap) {
        return noticeMapper.findTotalCnt(searchMap);
    }
}
