package com.bit.springboard.dto;

import com.bit.springboard.entity.FreeBoard;
import com.bit.springboard.entity.FreeBoardFile;
import com.bit.springboard.entity.Notice;
import com.bit.springboard.entity.NoticeFile;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BoardFileDto {
    private Long id;
    private Long board_id;
    private String filename;
    private String fileoriginname;
    private String filepath;
    private String filetype;
    private String filestatus;
    private String newfilename;

    public FreeBoardFile toFreeBoardFileEntity(FreeBoard freeBoard) {
        return FreeBoardFile.builder()
                .id(this.id)
                .freeBoard(freeBoard)
                .filename(this.filename)
                .fileoriginname(this.fileoriginname)
                .filepath(this.filepath)
                .filetype(this.filetype)
                .build();
    }

    public NoticeFile toNoticeFileEntity(Notice notice) {
        return NoticeFile.builder()
                .id(this.id)
                .notice(notice)
                .filename(this.filename)
                .fileoriginname(this.fileoriginname)
                .filepath(this.filepath)
                .filetype(this.filetype)
                .build();
    }
}
