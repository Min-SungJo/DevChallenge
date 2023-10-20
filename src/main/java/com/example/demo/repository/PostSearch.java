package com.example.demo.repository;

import com.example.demo.domain.PostCategory;
import com.example.demo.domain.PostStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * 작성자, 제목, 게시글 상태
 */
@Getter
@Setter
public class PostSearch {
    private String writer;
    private PostCategory category; // 게시글 카테고리 [질문 글, 일반 글, 프로젝트 글]
    private String title;
    private PostStatus postStatus; // 게시글 상태 [WRITE, DELETE]
}
