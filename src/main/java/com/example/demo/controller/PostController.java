package com.example.demo.controller;

import com.example.demo.domain.Post;
import com.example.demo.domain.PostStatus;
import com.example.demo.dto.SimplePostDto;
import com.example.demo.dto.UpdatePostDto;
import com.example.demo.repository.PostSearch;
import com.example.demo.service.MemberService;
import com.example.demo.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.*;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/posts/v1")
    public String postListV1(@ModelAttribute("postSearch") PostSearch postSearch, Model model) {
        List<Post> posts = postService.findPosts();
        model.addAttribute("posts", posts);
        return "posts/postList";
    }

    @GetMapping("/")
    public String postListV2(@ModelAttribute("searchCondition") SearchConditionForm searchCondition, Model model) {
        PostSearch postSearch = new PostSearch();
        if (searchCondition.getTitle() != null) {
            if (searchCondition.getTitle().equals("writer")) {
                postSearch.setWriter(searchCondition.getContents());
            }
            if (searchCondition.getTitle().equals("title")) {
                postSearch.setTitle(searchCondition.getContents());
            }
        }
        postSearch.setPostStatus(PostStatus.WRITE);
        List<Post> posts = postService.findPostsWithFilter(postSearch);
        List<SimplePostDto> result = posts.stream()
                .map(SimplePostDto::new)
                .collect(toList());
        model.addAttribute("posts", result);
        return "main";
    }

    @GetMapping("/posts/write")
    public String createForm(Model model, HttpSession session) {
        if (!memberService.isLogin(session)) return "redirect:/";
        model.addAttribute("form", new PostForm());
        return "posts/createPostForm";
    }

    @PostMapping("/posts/write")
    public String create(PostForm form, HttpSession session) {
        if (!memberService.isLogin(session)) return "redirect:/";
        form.setDate(new Date());
        form.setWriter(session.getAttribute("loginMember").toString());
        postService.write(form);
        return "redirect:/";
    }

    @GetMapping("/posts/{postId}")
    public String viewPostDetail(@PathVariable("postId") Long postId, Model model) {
        Post post = postService.findOne(postId);
        PostForm form = PostForm.createForm(
                post.getId(),
                post.getTitle(),
                post.getContents(),
                post.getDate(),
                post.getMember().getNickname(),
                post.getViewCount());
        model.addAttribute("form", form);
        postService.plusViewCount(post);
        return "posts/postDetail";
    }

    @GetMapping("/posts/{postId}/edit")
    public String updatePostForm(@PathVariable("postId") Long postId, Model model, HttpSession session) {
        if (!memberService.isLogin(session)) return "redirect:/";
        Post post = postService.findOne(postId);
        PostForm form = PostForm.createForm(
                post.getId(),
                post.getTitle(),
                post.getContents(),
                post.getDate(),
                post.getMember().getNickname(),
                post.getViewCount());
        model.addAttribute("form", form);
        return "posts/updatePostForm";
    }

    @PostMapping("/posts/{postId}/edit")
    public String updatePost(@PathVariable Long postId, @ModelAttribute("form") PostForm form, HttpSession session) {
        if (!memberService.isLogin(session)) return "redirect:/";
        UpdatePostDto postDto = UpdatePostDto.createUpdatePostDto(
                postId, form.getTitle(), form.getContents(), new Date()
        );
        postService.updatePost(postDto);
        return "redirect:/";
    }

    @PostMapping("/posts/{postId}/delete")
    public String deletePost(@PathVariable Long postId, HttpSession session) {
        if (!memberService.isLogin(session)) return "redirect:/";
        postService.deletePost(postId, session.getAttribute("loginMember").toString());
        return "redirect:/";
    }

}
