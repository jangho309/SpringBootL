package com.bit.springboard.service.impl;

import com.bit.springboard.common.FileUtils;
import com.bit.springboard.config.QueryDslConfiguration;
import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.entity.FreeBoard;
import com.bit.springboard.entity.Member;
import com.bit.springboard.mapper.FreeMapper;
import com.bit.springboard.repository.FreeBoardFileRepository;
import com.bit.springboard.repository.FreeBoardRepository;
import com.bit.springboard.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Import(QueryDslConfiguration.class)
public class FreeBoardServiceTest {
    // Mokito 클래스를 이용해서 가짜 객체를 생성하고 의존성 주입
    private FreeMapper freeMapper = Mockito.mock(FreeMapper.class);
    private FileUtils fileUtils = Mockito.mock(FileUtils.class);
    private FreeBoardRepository freeBoardRepository = Mockito.mock(FreeBoardRepository.class);
    private MemberRepository memberRepository = Mockito.mock(MemberRepository.class);
    private FreeBoardFileRepository freeBoardFileRepository = Mockito.mock(FreeBoardFileRepository.class);

    // 서비스 클래스의 메소드 호출을 위한 객체 생성
    private FreeServiceImpl freeService;

    // @BeforeEach: 각각의 테스트 코드가 실행되기전에 실행할 코드 지정
    @BeforeEach
    public void setUpTest(){
        freeService = new FreeServiceImpl(
                freeMapper, fileUtils,
                freeBoardRepository, memberRepository, freeBoardFileRepository);
    }

    @Test
    void findByIdTest() throws Exception{
        // 기대 값을 가지고 있는 객체
        FreeBoard board = FreeBoard.builder()
                .id(1L)
                .title("제목1")
                .content("내용")
                .cnt(2)
                .regdate(LocalDateTime.parse("2024-08-23T16:56:09.059281"))
                .moddate(LocalDateTime.parse("2024-08-23T16:56:09.059281"))
                .member(Member.builder()
                        .id(1L)
                        .nickname("에이피아이_비트1")
                        .build())
                .boardFileList(new ArrayList<>())
                .build();

        // Mockito 클래스를 이용한 테스트 계획 수립
        Mockito.when(freeBoardRepository.searchOne(1L))
                .thenReturn(Optional.of(board));

        BoardDto findByIdBoardDto = freeService.findById(1L);

        // Assertions 클래스를 이용한 검증
        Assertions.assertEquals(board.getId(), findByIdBoardDto.getId());
        Assertions.assertEquals(board.getTitle(), findByIdBoardDto.getTitle());
        Assertions.assertEquals(board.getContent(), findByIdBoardDto.getContent());
        Assertions.assertEquals(board.getMember().getId(), findByIdBoardDto.getWriter_id());
        Assertions.assertEquals(board.getMember().getNickname(), findByIdBoardDto.getNickname());
        Assertions.assertEquals(board.getRegdate(), findByIdBoardDto.getRegdate());
        Assertions.assertEquals(board.getModdate(), findByIdBoardDto.getModdate());

        // verify 메소드를 통해 freeBoardRepository의 searchOne이 한 번 호출됐는 지 검증
        verify(freeBoardRepository, times(1)).searchOne(1L);
    }
}
