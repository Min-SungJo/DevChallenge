package com.example.demo.controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchConditionForm {
    private String title; // writer, title
    private String contents;

    public static SearchConditionForm createSearchConditionDto(String title, String contents) {
        SearchConditionForm dto = new SearchConditionForm();
        dto.title = title;
        dto.contents = contents;
        return dto;
    }
}
