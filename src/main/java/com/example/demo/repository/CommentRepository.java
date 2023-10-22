package com.example.demo.repository;

import com.example.demo.domain.Comment;
import com.example.demo.domain.PostStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
    }

    public Optional<Comment> findOne(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }

    public List<Comment> findAllWithPost(CommentSearch commentSearch) {
        System.out.println("값!!!!");
        System.out.println(commentSearch.getCommentStatus());
        System.out.println(commentSearch.getPostId());
        System.out.println(commentSearch.getContents());
        System.out.println(commentSearch.getWriter());
        String jpql = "select c from Comment c join c.post p join c.member m";
        boolean isFirstCondition = true;

        // 게시글 상태 확인
        if (commentSearch.getCommentStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " c.status = :status";
        }

        // 특정 게시글에 속한 댓글 검색
        if (commentSearch.getPostId() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " p.post_id = :postId";
        }

        //회원 이름 검색
        if (StringUtils.hasText(commentSearch.getWriter())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.nickname like :nickname";
        }

        // 내용 검색
        if (StringUtils.hasText(commentSearch.getContents())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " c.contents like :contents";
        }

        TypedQuery<Comment> query = em.createQuery(jpql, Comment.class)
                .setMaxResults(1000);
        if (commentSearch.getCommentStatus() != null) {
            query = query.setParameter("status", commentSearch.getCommentStatus());
        }
        if (commentSearch.getPostId() != null) {
            query = query.setParameter("postId", commentSearch.getPostId());
        }
        if (StringUtils.hasText(commentSearch.getWriter())) {
            query = query.setParameter("nickname", commentSearch.getWriter());
        }
        if (StringUtils.hasText(commentSearch.getContents())) {
            query = query.setParameter("contents", "%" + commentSearch.getContents() + "%");
        }
        return query.getResultList();
    }
    public List<Comment> findAllWithPost(Long postId) {
        System.out.println("값!!!!");
        System.out.println(postId);
        String jpql = "select c from Comment c join c.post p where c.status = :status";

        // 특정 게시글에 속한 댓글 검색
        if (postId != null) {
            jpql += " and p.post_id = :postId";
        }
        TypedQuery<Comment> query = em.createQuery(jpql, Comment.class)
                .setMaxResults(1000);
        query = query.setParameter("status", PostStatus.WRITE);
        if (postId != null) {
            query = query.setParameter("postId", postId);
        }
        return query.getResultList();
    }
}
