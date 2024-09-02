package com.bit.springboard.entity;

import com.bit.springboard.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
//@Table(name = "T_MEMBER")
@Getter
@Setter
// Null 값이 입력되면 Default 값으로 insert 함
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(unique = true, length = 50)
    private String username;

    private String email;

    @Column(length = 300)
    private String password;

    @Column(unique = true)
    private String nickname;

    private String tel;

    @ColumnDefault("'ROLE_USER'")
    private String role;

    private LocalDateTime lastLoginDate;

    private boolean active;
//    @Transient
//    private LocalDateTime regdate;

    public MemberDto toDto() {
        return MemberDto.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .nickname(this.nickname)
                .tel(this.tel)
                .role(this.role)
                .active(this.active)
                .lastLoginDate(this.lastLoginDate)
                .build();
    }
}
