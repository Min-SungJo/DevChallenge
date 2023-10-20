package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // new Post 방지
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(value = EnumType.STRING)
    private PostCategory category; // 게시글 범주 [질문 글, 일반 글]

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;
    @Column(nullable = false)
    private Date date;
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int viewCount;

    @Enumerated(value = EnumType.STRING)
    private PostStatus status; // 게시글 상태 [WRITE, DELETE]

    public static Post createPost(Member member,PostCategory category, String title, String contents, Date date) {
        Post post = new Post();
        post.member = member;
        post.category = category;
        post.title = title;
        post.contents = contents;
        post.date = date;
        post.status = PostStatus.WRITE;
        post.viewCount = 0;
        return post;
    }

    public void plusViewCount() {
        this.viewCount += 1;
    }

    public void updatePost(PostCategory category, String title, String contents, Date date) {
        this.category = category;
        this.title = title;
        this.contents = contents;
        this.date = date;
    }

    public void delete() {
        this.status = PostStatus.DELETE;
    }


}
