package com.example.demo.service;

import com.example.demo.controller.PostForm;
import com.example.demo.domain.Member;
import com.example.demo.domain.Post;
import com.example.demo.domain.PostStatus;
import com.example.demo.dto.UpdatePostDto;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.PostSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시글 작성
     */
    @Transactional
    public Long write(PostForm form) {
        //엔티티 조회
        Member member = memberRepository.findByNickname(form.getWriter()).orElse(null);
        //게시글 정보 생성
        Post post = Post.createPost(member, form.getTitle(), form.getContents(), form.getDate());
        //게시글 저장
        postRepository.save(post);
        return post.getId();
    }

    /**
     * 게시글 조회
     */
    public Post findOne(Long postId) {
        return postRepository.findOne(postId);
    }

    /**
     * 게시글 전체 조회
     */
    public List<Post> findPosts() {
        return postRepository.findAll();
    }

    /**
     * 게시글 검색 필터 조회
     */
    public List<Post> findPostsWithFilter(PostSearch postSearch) {
        return postRepository.findAllWithFilter(postSearch);
    }

    /**
     * 조회수 증가
     */
    @Transactional
    public void plusViewCount(Post post) {
        post.plusViewCount();
        postRepository.save(post);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public void updatePost(UpdatePostDto postDto) {
        //엔티티 조회
        Post post = postRepository.findOne(postDto.getPostId());
        post.updatePost(postDto.getTitle(), postDto.getContents(), postDto.getDate());
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deletePost(Long postId, String requestMember) {
        //게시글 엔티티 조회
        Post post = postRepository.findOne(postId);
        if(post.getMember().getNickname().equals(requestMember)) {
            post.delete();
        }
    }
}
