package com.bit.springboard.entity;

import com.bit.springboard.dto.BoardDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(
        name = "NoticeSeqGenerator",
        sequenceName = "notice_seq",
        initialValue = 1,
        allocationSize = 1
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Notice {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "NoticeSeqGenerator"
    )
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private Member member;

    private LocalDateTime regdate;
    private LocalDateTime moddate;

    private int cnt;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<NoticeFile> noticeFileList = new ArrayList<>();

    public BoardDto toDto() {
        return BoardDto.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .writer_id((this.member.getId()))
                .nickname(this.member.getNickname())
                .regdate(this.regdate)
                .moddate(this.moddate)
                .cnt(this.cnt)
                .boardFileDtoList(
                        this.noticeFileList.size() > 0
                                ? this.noticeFileList.stream().map(NoticeFile::toDto).toList()
                                : new ArrayList<>()
                )
                .build();
    }
}
