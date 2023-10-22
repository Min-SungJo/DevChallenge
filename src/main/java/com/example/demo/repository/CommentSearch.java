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
public class CommentSearch {
    private Long postId;
    private String writer;
    private String contents;
    private PostStatus commentStatus; // 게시글 상태 [WRITE, DELETE]
}
