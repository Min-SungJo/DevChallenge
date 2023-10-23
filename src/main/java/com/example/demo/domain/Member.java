package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // new Member 방지
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String nickname;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role; // 권한,상태 [ROLE_ADMIN, ROLE_USER, ROLE_BAN]

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Project> projects;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Comment> comments;

    public static Member createMember(String nickname, String password) { // setter 대체
        Member member = new Member();
        member.nickname = nickname;
        member.password = password;
        member.role = Role.ROLE_USER;
        return member;
    }

    public void updateMember(String password) { // setter 대체
        this.password = password;
    }

    public void deleteMember() {
        this.role = Role.ROLE_BAN;
    }
}
