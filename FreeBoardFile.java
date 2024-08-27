package com.bit.springboard.entity;

import com.bit.springboard.dto.BoardFileDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Setter
@Table(name = "freeboard_file")
@SequenceGenerator(
        name = "FreeBoardFileSeqGenerator",
        sequenceName = "freeboard_file_seq",
        initialValue = 1,
        allocationSize = 1
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreeBoardFile {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "FreeBoardFileSeqGenerator"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @JsonBackReference
    private FreeBoard freeBoard;

    private String filename;

    private String fileoriginname;

    private String filepath;

    private String filetype;

    public BoardFileDto toDto() {
        return BoardFileDto.builder()
                .id(this.id)
                .board_id(this.freeBoard.getId())
                .filename(this.filename)
                .fileoriginname(this.fileoriginname)
                .filepath(this.filepath)
                .filetype(this.filetype)
                .build();
    }
}
