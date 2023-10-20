package com.example.demo.controller;

import com.example.demo.domain.PostCategory;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostForm {

    private Long id;

    @NotEmpty(message = "카테고리를 입력하세요")
    private PostCategory category;

    @NotEmpty(message = "제목을 입력하세요")
    private String title;
    @NotEmpty(message = "내용을 입력하세요")
    private String contents;
    private Date date;
    private String writer;

    private int viewCount;

    public static PostForm createForm(Long id, PostCategory category, String title, String contents, Date date, String writer, int viewCount) {
        PostForm form = new PostForm();
        form.id = id;
        form.category = category;
        form.title = title;
        form.contents = contents;
        form.date = date;
        form.writer = writer;
        form.viewCount = viewCount;
        return form;
    }

}
