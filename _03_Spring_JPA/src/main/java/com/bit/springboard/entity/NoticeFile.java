package com.bit.springboard.entity;

import com.bit.springboard.dto.BoardFileDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name = "NoticeFileSeqGenerator",
        sequenceName = "notice_file_seq",
        initialValue = 1,
        allocationSize = 1
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeFile {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "NoticeFileSeqGenerator"
    )
    private long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @JsonBackReference
    private Notice notice;

    private String filename;

    private String fileoriginname;

    private String filepath;

    private String filetype;

    public BoardFileDto toDto(){
        return BoardFileDto.builder()
                .id(this.id)
                .board_id(this.notice.getId())
                .filename(this.filename)
                .fileoriginname(this.fileoriginname)
                .filepath(this.filepath)
                .filetype(this.filetype)
                .build();
    }
}
