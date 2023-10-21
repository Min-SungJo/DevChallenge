package com.example.demo.service;

import com.example.demo.controller.ProjectForm;
import com.example.demo.domain.Member;
import com.example.demo.domain.Project;
import com.example.demo.dto.UpdateProjectDto;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.ProjectSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시글 작성
     */
    @Transactional
    public Long write(ProjectForm form) {
        //엔티티 조회
        Member member = memberRepository.findByNickname(form.getWriter()).orElse(null);
        //프로젝트 정보 생성
        Project project = Project.createProject(
                member,
                form.getTitle(),
                form.getSubject(),
                form.getSummary(),
                form.getMen(),
                form.getPeriod(),
                form.getSkills(),
                form.getFunctions(),
                form.getTroubleshooting(),
                form.getReview(),
                form.getUrl(),
                form.getDate());
        //게시글 저장
        projectRepository.save(project);
        return project.getId();
    }

    /**
     * 게시글 조회
     */
    public Project findOne(Long projectId) {
        return projectRepository.findOne(projectId);
    }

    /**
     * 게시글 전체 조회
     */
    public List<Project> findProjects() {
        return projectRepository.findAll();
    }

    /**
     * 게시글 검색 필터 조회
     */
    public List<Project> findProjectsWithFilter(ProjectSearch projectSearch) {
        return projectRepository.findAllWithFilter(projectSearch);
    }

    /**
     * 조회수 증가
     */
    @Transactional
    public void plusViewCount(Project project) {
        project.plusViewCount();
        projectRepository.save(project);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public void updateProject(UpdateProjectDto projectDto) {
        //엔티티 조회
        Project project = projectRepository.findOne(projectDto.getProjectId());
        project.updateProject(
                projectDto.getTitle(),
                projectDto.getSubject(),
                projectDto.getSummary(),
                projectDto.getMen(),
                projectDto.getPeriod(),
                projectDto.getSkills(),
                projectDto.getFunctions(),
                projectDto.getTroubleshooting(),
                projectDto.getReview(),
                projectDto.getUrl(),
                projectDto.getDate()
        );
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deleteProject(Long projectId, String requestMember) {
        //게시글 엔티티 조회
        Project project = projectRepository.findOne(projectId);
        if(project.getMember().getNickname().equals(requestMember)) {
            project.delete();
        }
    }
}
