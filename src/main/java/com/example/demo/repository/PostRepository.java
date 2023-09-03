package com.example.demo.repository;

import com.example.demo.domain.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) {
        if (post.getId() == null) {
            em.persist(post);
        } else {
            em.merge(post);
        }
    }

    public Post findOne(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public List<Post> findAllWithFilter(PostSearch postSearch) {
        String jpql = "select p from Post p join p.member m";
        boolean isFirstCondition = true;

        // 게시글 상태 확인
        if (postSearch.getPostStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " p.status = :status";
        }

        //회원 이름 검색
        if (StringUtils.hasText(postSearch.getWriter())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.nickname like :nickname";
        }

        // 제목 검색
        if (StringUtils.hasText(postSearch.getTitle())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " p.title like :title";
        }
        TypedQuery<Post> query = em.createQuery(jpql, Post.class)
                .setMaxResults(1000);
        if (postSearch.getPostStatus() != null) {
            query = query.setParameter("status", postSearch.getPostStatus());
        }
        if (StringUtils.hasText(postSearch.getWriter())) {
            query = query.setParameter("nickname", postSearch.getWriter());
        }
        if (StringUtils.hasText(postSearch.getTitle())) {
            query = query.setParameter("title", "%" + postSearch.getTitle() + "%");
        }
        return query.getResultList();
    }

}
