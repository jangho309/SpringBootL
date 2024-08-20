package com.bit.springboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardFileDto {
    private int id;
    private int board_id;
    private String filename;
    private String fileoriginname;
    private String filepath;
    private String filetype;
    private String filestatus;
    private String newfilename;
}
