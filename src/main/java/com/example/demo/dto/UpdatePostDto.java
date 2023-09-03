package com.example.demo.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdatePostDto {

    private Long postId;
    private String title;
    private String contents;
    private Date date;

    public static UpdatePostDto createUpdatePostDto(Long postId, String title, String contents, Date date) {
        UpdatePostDto dto = new UpdatePostDto();
        dto.postId = postId;
        dto.title = title;
        dto.contents = contents;
        dto.date = date;
        return dto;
    }
}
