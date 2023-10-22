package com.example.demo.controller;

import com.example.demo.domain.Comment;
import com.example.demo.domain.Post;
import com.example.demo.domain.PostCategory;
import com.example.demo.domain.PostStatus;
import com.example.demo.dto.SimplePostDto;
import com.example.demo.dto.UpdatePostDto;
import com.example.demo.repository.PostSearch;
import com.example.demo.service.CommentService;
import com.example.demo.service.MemberService;
import com.example.demo.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.*;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;

    @ModelAttribute("categories")
    public PostCategory[] categories() {
        return PostCategory.values();
    }

    @GetMapping("/posts/v1")
    public String postListV1(@ModelAttribute("postSearch") PostSearch postSearch, Model model) {
        List<Post> posts = postService.findPosts();
        model.addAttribute("posts", posts);
        return "posts/postList";
    }

    @GetMapping("/")
    public String postListV2(HttpServletRequest request, Model model, @ModelAttribute("searchCondition") SearchConditionForm searchCondition) {
        PostSearch postSearch = new PostSearch();
        // 작성자 검색이면 작성자를 채우고, 컨텐츠 검색이면 컨텐츠를 채운다
        String title;
        if (request.getParameter("title") != null) { // 검색 필터링 사용 시
            title = request.getParameter("title");
            String contents = request.getParameter("contents");
            if (title.equals("writer")) { //작성자 검색이면
                postSearch.setWriter(contents);
            }
            if (title.equals("title")) { // 제목 검색이면
                postSearch.setTitle(contents);
            }
            // 카테고리 검색
            if (request.getParameter("category") != null && request.getParameter("category").equals("ALL")) {
                String category = request.getParameter("category");
                postSearch.setCategory(PostCategory.valueOf(category));
            }
        }
        postSearch.setPostStatus(PostStatus.WRITE); // 삭제되지 않은 게시글 보기
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
                post.getCategory(),
                post.getTitle(),
                post.getContents(),
                post.getDate(),
                post.getMember().getNickname(),
                post.getViewCount());
        model.addAttribute("form", form);
        postService.plusViewCount(post);
        List<Comment> comments = commentService.findAllWithPost(postId);
        model.addAttribute("comments", comments);
        return "posts/postDetail";
    }

    @GetMapping("/posts/{postId}/edit")
    public String updatePostForm(@PathVariable("postId") Long postId, Model model, HttpSession session) {
        if (!memberService.isLogin(session)) return "redirect:/";
        Post post = postService.findOne(postId);
        PostForm form = PostForm.createForm(
                post.getId(),
                post.getCategory(),
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
        if (!memberService.isLogin(session)) return "redirect:/posts/"+postId;
        UpdatePostDto postDto = UpdatePostDto.createUpdatePostDto(
                postId, form.getCategory(), form.getTitle(), form.getContents(), new Date()
        );
        postService.updatePost(postDto);
        return "redirect:/posts/"+postId;
    }

    @PostMapping("/posts/{postId}/delete")
    public String deletePost(@PathVariable Long postId, HttpSession session) {
        if (!memberService.isLogin(session)) return "redirect:/";
        postService.deletePost(postId, session.getAttribute("loginMember").toString());
        return "redirect:/";
    }

}
