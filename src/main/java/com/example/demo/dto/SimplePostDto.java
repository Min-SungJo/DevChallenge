package com.example.demo.dto;

import com.example.demo.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimplePostDto {
    private Long postId;
    private String title;
    private String contents;
    private Date date;
    private String writer;

    private int viewCount;

    public SimplePostDto(Post post) {
        postId = post.getId();
        title = post.getTitle();
        contents = post.getContents();
        date = post.getDate();
        writer = post.getMember().getNickname();
        viewCount = post.getViewCount();
    }

    public static SimplePostDto createSimplePostDto(Long postId, String title, String contents, Date date, String writer, int viewCount) {
        SimplePostDto dto = new SimplePostDto();
        dto.postId = postId;
        dto.title = title;
        dto.contents = contents;
        dto.date = date;
        dto.writer = writer;
        dto.viewCount = viewCount;
        return dto;
    }
}
