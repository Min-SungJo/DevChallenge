package com.example.demo.controller;

import com.example.demo.domain.PostStatus;
import com.example.demo.domain.Project;
import com.example.demo.dto.SimpleProjectDto;
import com.example.demo.dto.UpdateProjectDto;
import com.example.demo.repository.ProjectSearch;
import com.example.demo.service.MemberService;
import com.example.demo.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final MemberService memberService;

    @GetMapping("")
    public String projectListV2(HttpServletRequest request, Model model, @ModelAttribute("searchCondition") SearchConditionForm searchCondition) {
        ProjectSearch projectSearch = new ProjectSearch();
        // 작성자 검색이면 작성자를 채우고, 컨텐츠 검색이면 컨텐츠를 채운다
        String title = null;
        if (request.getParameter("title") != null) { // 값이 있고
            title = request.getParameter("title");
            String contents = request.getParameter("contents");
            if (title.equals("writer")) { //작성자 검색이면
                projectSearch.setWriter(contents);
            }
            if (title.equals("title")) { // 제목 검색이면
                projectSearch.setTitle(contents);
            }
            if (title.equals("skills")) { // 기술 검색이면
                projectSearch.setSkills(contents);
            }
            if (title.equals("summary")) { // 요약본 검색이면
                projectSearch.setSummary(contents);
            }
        }
        projectSearch.setPostStatus(PostStatus.WRITE); // 삭제되지 않은 게시글 보기
        List<Project> projects = projectService.findProjectsWithFilter(projectSearch);
        List<SimpleProjectDto> result = projects.stream()
                .map(SimpleProjectDto::new)
                .collect(toList());
        model.addAttribute("projects", result);
        return "posts/projects/projectMain";
    }

    @GetMapping("/write")
    public String createForm(Model model, HttpSession session) {
        if (!memberService.isLogin(session)) return "redirect:/";
        model.addAttribute("form", new ProjectForm());
        return "posts/projects/createProjectForm";
    }

    @PostMapping("/write")
    public String create(ProjectForm form, HttpSession session) {
        if (!memberService.isLogin(session)) return "redirect:/";
        form.setDate(new Date());
        form.setWriter(session.getAttribute("loginMember").toString());
        projectService.write(form);
        return "redirect:/projects";
    }

    @GetMapping("/{projectId}")
    public String viewProjectDetail(@PathVariable("projectId") Long projectId, Model model) {
        Project project = projectService.findOne(projectId);
        ProjectForm form = ProjectForm.createForm(
                project.getId(),
                project.getTitle(),
                project.getSubject(),
                project.getSummary(),
                project.getMen(),
                project.getPeriod(),
                project.getSkills(),
                project.getFunctions(),
                project.getTroubleshooting(),
                project.getReview(),
                project.getUrl(),
                project.getDate(),
                project.getMember().getNickname(),
                project.getViewCount());
        model.addAttribute("form", form);
        projectService.plusViewCount(project);
        return "posts/projects/projectDetail";
    }

    @GetMapping("/{projectId}/edit")
    public String updateProjectForm(@PathVariable("projectId") Long projectId, Model model, HttpSession session) {
        if (!memberService.isLogin(session)) return "redirect:/projects";
        Project project = projectService.findOne(projectId);
        ProjectForm form = ProjectForm.createForm(
                project.getId(),
                project.getTitle(),
                project.getSubject(),
                project.getSummary(),
                project.getMen(),
                project.getPeriod(),
                project.getSkills(),
                project.getFunctions(),
                project.getTroubleshooting(),
                project.getReview(),
                project.getUrl(),
                project.getDate(),
                project.getMember().getNickname(),
                project.getViewCount());
        model.addAttribute("form", form);
        return "posts/projects/updateProjectForm";
    }

    @PostMapping("/{projectId}/edit")
    public String updateProject(@PathVariable Long projectId, @ModelAttribute("form") ProjectForm form, HttpSession session) {
        if (!memberService.isLogin(session)) return "redirect:/projects";
        UpdateProjectDto postDto = UpdateProjectDto.createUpdateProjectDto(
                projectId,
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
                new Date()
        );
        projectService.updateProject(postDto);
        return "redirect:/projects";
    }

    @PostMapping("/{projectId}/delete")
    public String deleteProject(@PathVariable Long projectId, HttpSession session) {
        if (!memberService.isLogin(session)) return "redirect:/projects";
        projectService.deleteProject(projectId, session.getAttribute("loginMember").toString());
        return "redirect:/projects";
    }

}
