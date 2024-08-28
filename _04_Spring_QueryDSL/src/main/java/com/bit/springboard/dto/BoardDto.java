package com.bit.springboard.dto;

import com.bit.springboard.entity.FreeBoard;
import com.bit.springboard.entity.Member;
import com.bit.springboard.entity.Notice;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BoardDto {
    private String type = "free";
    private Long id;
    private String title;
    private String content;
    private Long writer_id;
    private String nickname;
    private LocalDateTime regdate;
    private LocalDateTime moddate;
    private int cnt;
    private List<BoardFileDto> boardFileDtoList = new ArrayList<>();

    public FreeBoard toFreeBoardEntity(Member member) {
        return FreeBoard.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .member(member)
                .cnt(this.cnt)
                .regdate(this.regdate)
                .moddate(this.moddate)
                .boardFileList(new ArrayList<>())
                .build();
    }

    public Notice toNoticeEntity(Member member) {
        return Notice.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .member(member)
                .cnt(this.cnt)
                .regdate(this.regdate)
                .moddate(this.moddate)
                .noticeFileList(new ArrayList<>())
                .build();
    }
}
