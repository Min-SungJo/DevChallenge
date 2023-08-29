package com.example.demo.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "닉네임을 입력하세요")
    private String nickname;
    @NotEmpty(message = "비밀번호를 입력하세요")
    private String password;
}
