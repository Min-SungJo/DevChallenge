package com.example.demo.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentForm {

    private Long id;

    private Long postId;

    @NotEmpty(message = "내용을 입력하세요")
    private String contents;
    private Date date;
    private String writer;

    public static CommentForm createForm(Long id, Long postId, String contents, Date date, String writer) {
        CommentForm form = new CommentForm();
        form.id = id;
        form.postId = postId;
        form.contents = contents;
        form.date = date;
        form.writer = writer;
        return form;
    }

}
