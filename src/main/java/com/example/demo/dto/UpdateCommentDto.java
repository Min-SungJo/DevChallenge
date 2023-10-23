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
public class UpdateCommentDto {

    private Long commentId;
    private Long postId;
    private String contents;
    private Date date;

    public static UpdateCommentDto createUpdateCommentDto(Long commentId, Long postId, String contents, Date date) {
        UpdateCommentDto dto = new UpdateCommentDto();
        dto.commentId = commentId;
        dto.postId = postId;
        dto.contents = contents;
        dto.date = date;
        return dto;
    }
}
