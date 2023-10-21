package com.example.demo.repository;

import com.example.demo.domain.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectRepository {

    private final EntityManager em;

    public void save(Project project) {
        if (project.getId() == null) {
            em.persist(project);
        } else {
            em.merge(project);
        }
    }

    public Project findOne(Long id) {
        return em.find(Project.class, id);
    }

    public List<Project> findAll() {
        return em.createQuery("select p from Project p", Project.class)
                .getResultList();
    }

    public List<Project> findAllWithFilter(ProjectSearch projectSearch) {
        System.out.println("값!!!!");
        System.out.println(projectSearch.getPostStatus());
        System.out.println(projectSearch.getTitle());
        System.out.println(projectSearch.getWriter());
        String jpql = "select p from Project p join p.member m";
        boolean isFirstCondition = true;

        // 게시글 상태 확인
        if (projectSearch.getPostStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " p.status = :status";
        }

        //회원 이름 검색
        if (StringUtils.hasText(projectSearch.getWriter())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.nickname like :nickname";
        }

        // 제목 검색
        if (StringUtils.hasText(projectSearch.getTitle())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " p.title like :title";
        }

        // 기술 검색
        if(StringUtils.hasText(projectSearch.getSkills())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " p.skills like :skills";
        }

        // 요약
        if(StringUtils.hasText(projectSearch.getSummary())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " p.summary like :summary";
        }

        TypedQuery<Project> query = em.createQuery(jpql, Project.class)
                .setMaxResults(1000);
        if (projectSearch.getPostStatus() != null) {
            query = query.setParameter("status", projectSearch.getPostStatus());
        }
        if (StringUtils.hasText(projectSearch.getWriter())) {
            query = query.setParameter("nickname", projectSearch.getWriter());
        }
        if (StringUtils.hasText(projectSearch.getTitle())) {
            query = query.setParameter("title", "%" + projectSearch.getTitle() + "%");
        }
        if (StringUtils.hasText(projectSearch.getSkills())) {
            query = query.setParameter("skills", "%" + projectSearch.getSkills() + "%");
        }
        if(StringUtils.hasText(projectSearch.getSummary())) {
            query = query.setParameter("summary", "%" + projectSearch.getSummary() + "%");
        }
        return query.getResultList();
    }

}
