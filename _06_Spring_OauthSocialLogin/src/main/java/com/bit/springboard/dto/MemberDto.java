package com.bit.springboard.dto;

import com.bit.springboard.entity.Member;
import lombok.*;

// Lombok에서 제공하는 어노테이션으로 Getter, Setter, ToString 등의 메소드를 생성할 수 있다.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 생성자를 사용하지 않고 builder 메소드를 이용해서 객체를 생성하는 어노테이션
@Builder
public class MemberDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String tel;
    private String role;

    public Member toEntity() {
        return Member.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .nickname(this.nickname)
                .tel(this.tel)
                .role(this.role)
                .build();
    }
}
