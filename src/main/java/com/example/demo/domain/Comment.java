package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // new 방지
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;
    @Column(nullable = false)
    private Date date;

    @Enumerated(value = EnumType.STRING)
    private PostStatus status; // 게시글 상태 [WRITE, DELETE]

    public static Comment createComment(
            Member member,
            Post post,
            String contents,
            Date date) {
        Comment comment = new Comment();
        comment.member = member;
        comment.post = post;
        comment.contents = contents;
        comment.date = date;
        comment.status = PostStatus.WRITE;
        return comment;
    }


    public void updateComment(String contents, Date date) {
        this.contents = contents;
        this.date = date;
    }

    public void delete() {
        this.status = PostStatus.DELETE;
    }


}
