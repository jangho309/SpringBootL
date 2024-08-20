package com.bit.springboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Lombok에서 제공하는 어노테이션으로 Getter, Setter, ToString 등의 메소드를 생성할 수 있다.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private int id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String tel;
    private String role;
}
