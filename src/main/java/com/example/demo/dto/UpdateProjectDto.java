package com.example.demo.dto;

import com.example.demo.domain.PostCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateProjectDto {

    private Long projectId;
    private String title;
    private String subject;
    private String summary;
    private int men;
    private String period;
    private String skills;
    private String functions;
    private String troubleshooting;
    private String review;
    private String url;
    private Date date;

    public static UpdateProjectDto createUpdateProjectDto(
            Long projectId,
            String title,
            String subject,
            String summary,
            int men,
            String period,
            String skills,
            String functions,
            String troubleshooting,
            String review,
            String url,
            Date date
    ) {
        UpdateProjectDto dto = new UpdateProjectDto();
        dto.projectId = projectId;
        dto.title = title;
        dto.subject = subject;
        dto.summary = summary;
        dto.men = men;
        dto.period = period;
        dto.skills = skills;
        dto.functions = functions;
        dto.troubleshooting = troubleshooting;
        dto.review = review;
        dto.url = url;
        dto.date = date;
        return dto;
    }
}
