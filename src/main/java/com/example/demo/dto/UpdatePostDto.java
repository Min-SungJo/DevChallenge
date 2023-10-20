package com.example.demo.dto;

import com.example.demo.domain.PostCategory;
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
    private PostCategory category;
    private String title;
    private String contents;
    private Date date;

    public static UpdatePostDto createUpdatePostDto(Long postId, PostCategory category, String title, String contents, Date date) {
        UpdatePostDto dto = new UpdatePostDto();
        dto.postId = postId;
        dto.category = category;
        dto.title = title;
        dto.contents = contents;
        dto.date = date;
        return dto;
    }
}
