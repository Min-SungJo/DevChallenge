package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Table(name = "projects")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // new Project 방지
public class Project {

    @Id
    @GeneratedValue
    @Column(name = "project_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String title; // 이름
    @Column(columnDefinition = "TEXT")
    private String subject; // 주제
    @Column(columnDefinition = "TEXT")
    private String summary; // 요약
    @Column
    private int men; // 인력
    @Column
    private String periods; // 기간
    @Column(columnDefinition = "TEXT")
    private String skills; // 기술
    @Column(columnDefinition = "TEXT")
    private String functions; // 기능
    @Column(columnDefinition = "TEXT")
    private String troubleshooting; // 트러블 슈팅
    @Column(columnDefinition = "TEXT")
    private String review; // 리뷰

    @Column(columnDefinition = "TEXT")
    private String url; // 링크
    @Column(nullable = false)
    private Date date; // 작성일
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int viewCount; // 조회수

    @Enumerated(value = EnumType.STRING)
    private PostStatus status; // 게시글 상태 [WRITE, DELETE]

    public static Project createProject(
            Member member,
            String title,
            String subject,
            String summary,
            int men,
            String periods,
            String skills,
            String functions,
            String troubleshooting,
            String review,
            String url,
            Date date) {
        Project project = new Project();
        project.member = member;
        project.title = title;
        project.subject = subject;
        project.summary = summary;
        project.men = men;
        project.periods = periods;
        project.skills = skills;
        project.functions = functions;
        project.troubleshooting = troubleshooting;
        project.review = review;
        project.url = url;
        project.date = date;
        project.status = PostStatus.WRITE;
        project.viewCount = 0;
        return project;
    }

    public void plusViewCount() {
        this.viewCount += 1;
    }

    public void updateProject(
            String title,
            String subject,
            String summary,
            int men,
            String periods,
            String skills,
            String functions,
            String troubleshooting,
            String review,
            String url,
            Date date
    ) {
        this.title = title;
        this.subject = subject;
        this.summary = summary;
        this.men = men;
        this.periods = periods;
        this.skills = skills;
        this.functions = functions;
        this.troubleshooting = troubleshooting;
        this.review = review;
        this.url = url;
        this.date = date;
    }

    public void delete() {
        this.status = PostStatus.DELETE;
    }


}
