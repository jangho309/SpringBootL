package com.bit.springboard.service.impl;

import com.bit.springboard.common.FileUtils;
import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.BoardFileDto;
import com.bit.springboard.dto.Criteria;
import com.bit.springboard.entity.Member;
import com.bit.springboard.entity.Notice;
import com.bit.springboard.entity.NoticeFile;
import com.bit.springboard.mapper.NoticeMapper;
import com.bit.springboard.repository.MemberRepository;
import com.bit.springboard.repository.NoticeFileRepository;
import com.bit.springboard.repository.NoticeRepository;
import com.bit.springboard.service.BoardService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeFileRepository noticeFileRepository;

//    public NoticeServiceImpl(NoticeMapper noticeMapper, FileUtils fileUtils) {
//        this.noticeMapper = noticeMapper;
//        this.fileUtils = fileUtils;
//    }

    @Override
    public BoardDto post(BoardDto boardDto, MultipartFile[] uploadFiles) {
        Member writer = memberRepository.findByNickname(boardDto.getNickname())
                .orElseThrow(() -> new RuntimeException("member not exist"));

        boardDto.setRegdate(LocalDateTime.now());
        boardDto.setModdate(LocalDateTime.now());

        Notice notice = boardDto.toNoticeEntity(writer);

        if(uploadFiles != null && uploadFiles.length > 0) {
            Arrays.stream(uploadFiles).forEach(file -> {
                if(file.getOriginalFilename() != null && !file.getOriginalFilename().equals("")) {
                    BoardFileDto boardFileDto = fileUtils.parserFileInfo(file, "notice/");
                    NoticeFile noticeFile = boardFileDto.toNoticeFileEntity(notice);
                    notice.getNoticeFileList().add(noticeFile);
                }
            });
        }
        return noticeRepository.save(notice).toDto();
    }

    @Override
    public Page<BoardDto> findAll(Map<String, String> searchMap, Pageable pageable) {
        Page<Notice> noticePage = noticeRepository.searchAll(pageable, searchMap);

//        if(searchMap.get("searchKeyword") != null) {
//            noticePage = noticeRepository.findByTitleContainingOrContentContainingOrMemberNicknameContaining(
//                    pageable, searchMap.get("searchKeyword"), searchMap.get("searchKeyword"), searchMap.get("searchKeyword")
//            );
//        }

        return noticePage.map(Notice::toDto);
    }

    @Override
    public BoardDto findById(Long id) {
        return noticeRepository.searchOne(id).orElseThrow(
                () -> new RuntimeException("notice not exist")
        ).toDto();

//        return noticeRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("notice not exist")).toDto();
    }

    @Override
    public List<BoardFileDto> findFilesById(Long id) {
        List<NoticeFile> noticeFileList = noticeFileRepository.findByNoticeId(id);
        List<BoardFileDto> boardFileDtoList = new ArrayList<>();

        noticeFileList.forEach(noticeFile -> {
           boardFileDtoList.add(noticeFile.toDto());
        });
        return boardFileDtoList;
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

        Notice notice = noticeRepository.findById(boardDto.getId()).orElseThrow(
                () -> new RuntimeException("notice not exist")
        );

        notice.setTitle(boardDto.getTitle());
        notice.setContent(boardDto.getContent());
        notice.setModdate(LocalDateTime.now());

        uFilesList.forEach(boardFileDto -> {
           if(boardFileDto.getFilestatus().equals("U") ||
            boardFileDto.getFilestatus().equals("I")){
               notice.getNoticeFileList().add(boardFileDto.toNoticeFileEntity(notice));
           } else if(boardFileDto.getFilestatus().equals("D")){
               fileUtils.deleteFile("notice/", boardFileDto.getFilename());
               noticeFileRepository.delete(boardFileDto.toNoticeFileEntity(notice));
           }
        });

        return noticeRepository.save(notice).toDto();
    }

    @Override
    public void updateBoardCnt(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(
                () -> new RuntimeException("notice not exist")
        );

        notice.setCnt(notice.getCnt() + 1);

        noticeRepository.save(notice);
    }

    @Override
    public void remove(Long id) {
        List<NoticeFile> noticeFileList = noticeFileRepository.findByNoticeId(id);

        noticeFileList.forEach(noticeFile -> fileUtils.deleteFile("notice/", noticeFile.getFilename()));

        noticeRepository.deleteById(id);
    }

    @Override
    public int findTotalCnt(Map<String, String> searchMap) {
        return noticeMapper.findTotalCnt(searchMap);
    }
}
