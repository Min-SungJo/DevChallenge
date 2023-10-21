package com.example.demo.dto;

import com.example.demo.domain.Post;
import com.example.demo.domain.PostCategory;
import com.example.demo.domain.Project;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleProjectDto {
    private Long projectId;
    private String title;
    private String subject;
    private Date date;
    private String writer;

    private int viewCount;

    public SimpleProjectDto(Project project) {
        projectId = project.getId();
        title = project.getTitle();
        subject = project.getSubject();
        date = project.getDate();
        writer = project.getMember().getNickname();
        viewCount = project.getViewCount();
    }

    public static SimpleProjectDto createSimplePostDto(Long postId, String title, String subject, Date date, String writer, int viewCount) {
        SimpleProjectDto dto = new SimpleProjectDto();
        dto.projectId = postId;
        dto.title = title;
        dto.subject = subject;
        dto.date = date;
        dto.writer = writer;
        dto.viewCount = viewCount;
        return dto;
    }
}
