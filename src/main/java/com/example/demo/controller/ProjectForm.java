package com.example.demo.controller;

import com.example.demo.domain.PostCategory;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectForm {


    private Long id;

    @NotEmpty(message = "제목을 입력하세요")
    private String title;

    private String subject;
    private String summary;
    private int men;
    private String periods;
    private String skills;
    private String functions;
    private String troubleshooting;
    private String review;
    private String url;
    private Date date;
    private String writer;
    private int viewCount;

    public static ProjectForm createForm
            (Long id,
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
             Date date,
             String writer,
             int viewCount) {
        ProjectForm form = new ProjectForm();
        form.setId(id);
        form.setTitle(title);
        form.setSubject(subject);
        form.setSummary(summary);
        form.setMen(men);
        form.setPeriods(periods);
        form.setSkills(skills);
        form.setFunctions(functions);
        form.setTroubleshooting(troubleshooting);
        form.setReview(review);
        form.setUrl(url);
        form.setDate(date);
        form.setWriter(writer);
        form.setViewCount(viewCount);
        return form;
    }

}
