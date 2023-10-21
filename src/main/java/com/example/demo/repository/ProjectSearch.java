package com.example.demo.repository;

import com.example.demo.domain.PostStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * 작성자, 제목, 게시글 상태
 */
@Getter
@Setter
public class ProjectSearch{
    private String writer;
    private String title;
    private String skills;
    private String summary;
    private PostStatus postStatus; // 게시글 상태 [WRITE, DELETE]
}
