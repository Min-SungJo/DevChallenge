package com.example.demo.service;

import com.example.demo.controller.CommentForm;
import com.example.demo.domain.Comment;
import com.example.demo.domain.Member;
import com.example.demo.domain.Post;
import com.example.demo.dto.UpdateCommentDto;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    /**
     * 게시글 작성
     */
    @Transactional
    public Long write(CommentForm form) {
        //엔티티 조회
        Member member = memberRepository.findByNickname(form.getWriter()).orElse(null);
        Post post = postRepository.findOne(form.getPostId()).orElse(null);
        //게시글 정보 생성
        Comment comment = Comment.createComment(member, post, form.getContents(), form.getDate());
        //게시글 저장
        commentRepository.save(comment);
        return comment.getId();
    }

    /**
     * 댓글 조회
     */
    public Comment findOne(Long commentId) {
        return commentRepository.findOne(commentId).orElse(null);
    }

    /**
     * 댓글 전체 조회
     */
    public List<Comment> findComments() {
        return commentRepository.findAll();
    }

    /**
     * 게시글의 댓글 필터링 조회
     */
    public List<Comment> findAllWithFilter(CommentSearch commentSearch) {
        return commentRepository.findAllWithPost(commentSearch);
    }
    public List<Comment> findAllWithPost(Long postId) {
        return commentRepository.findAllWithPost(postId);
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public void updateComment(UpdateCommentDto commentDto) {
        //엔티티 조회
        Comment comment = commentRepository.findOne(commentDto.getCommentId()).orElse(null);
        comment.updateComment(commentDto.getContents(), commentDto.getDate());
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public void deleteComment(Long commentId, String requestMember) {
        //게시글 엔티티 조회
        Comment comment = commentRepository.findOne(commentId).orElse(null);
        if (comment.getMember().getNickname().equals(requestMember)) {
            comment.delete();
        }
    }
}
