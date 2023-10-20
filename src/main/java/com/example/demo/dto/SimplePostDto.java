package com.example.demo.dto;

import com.example.demo.domain.Post;
import com.example.demo.domain.PostCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimplePostDto {
    private Long postId;
    private PostCategory category;
    private String title;
    private String contents;
    private Date date;
    private String writer;

    private int viewCount;

    public SimplePostDto(Post post) {
        postId = post.getId();
        category = post.getCategory();
        title = post.getTitle();
        contents = post.getContents();
        date = post.getDate();
        writer = post.getMember().getNickname();
        viewCount = post.getViewCount();
    }

    public static SimplePostDto createSimplePostDto(Long postId, PostCategory category, String title, String contents, Date date, String writer, int viewCount) {
        SimplePostDto dto = new SimplePostDto();
        dto.postId = postId;
        dto.category = category;
        dto.title = title;
        dto.contents = contents;
        dto.date = date;
        dto.writer = writer;
        dto.viewCount = viewCount;
        return dto;
    }
}
