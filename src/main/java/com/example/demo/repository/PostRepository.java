package com.example.demo.repository;

import com.example.demo.domain.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

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

    public Optional<Post> findOne(Long id) {
        return Optional.ofNullable(em.find(Post.class, id));
    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public List<Post> findAllWithFilter(PostSearch postSearch) {
        System.out.println("값!!!!");
        System.out.println(postSearch.getPostStatus());
        System.out.println(postSearch.getTitle());
        System.out.println(postSearch.getWriter());
        System.out.println(postSearch.getCategory());
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

        // 카테고리 검색
        if (postSearch.getCategory() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " p.category like :category";
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
        if (postSearch.getCategory() != null) {
            query = query.setParameter("category", postSearch.getCategory());
        }
        return query.getResultList();
    }

}
