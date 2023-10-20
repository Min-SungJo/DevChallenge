package com.example.demo.controller;

import com.example.demo.domain.PostCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchConditionForm {
    private String title; // writer, title 작성자, 제목 검색
    private String contents;
    private PostCategory category;

    public static SearchConditionForm createSearchConditionDto(String title, String contents, PostCategory category) {
        SearchConditionForm dto = new SearchConditionForm();
        dto.title = title;
        dto.contents = contents;
        dto.category = category;
        return dto;
    }
}
