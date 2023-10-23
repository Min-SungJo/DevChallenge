package com.example.demo.controller;

import com.example.demo.dto.UpdateCommentDto;
import com.example.demo.service.CommentService;
import com.example.demo.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;

//    @GetMapping("/posts/comments/v1") // 댓글 전체
//    public String commentListV1(@ModelAttribute("postSearch") PostSearch postSearch, Model model) {
//        List<Post> posts = postService.findPosts();
//        model.addAttribute("posts", posts);
//        return "posts/comments/commentList";
//    }

    @PostMapping("/posts/{postId}/comments/write") // 댓글 쓰기
    public String create(@PathVariable("postId") Long postId, @ModelAttribute CommentForm form, HttpSession session) {
        if (!memberService.isLogin(session)) return "redirect:/posts/"+postId;
        form.setPostId(postId);
        form.setDate(new Date());
        form.setWriter(session.getAttribute("loginMember").toString());
        commentService.write(form);
        return "redirect:/posts/"+postId;
    }
    @PostMapping("/posts/{postId}/comments/{commentId}/edit") // 댓글 수정
    public String updateCommentForm(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, @ModelAttribute("commentForm") CommentForm form, HttpSession session) {
        if (!memberService.isLogin(session)) return "redirect:/posts/"+postId;
        UpdateCommentDto commentDto = UpdateCommentDto.createUpdateCommentDto(
                commentId,postId,form.getContents(), new Date());
        commentService.updateComment(commentDto);
        return "redirect:/posts/"+postId;
    }

    @PostMapping("/posts/{postId}/comments/{commentId}/delete") // 댓글 삭제
    public String deletePost(@PathVariable Long postId, @PathVariable Long commentId, HttpSession session) {
        if (!memberService.isLogin(session)) return "redirect:/posts/"+postId;
        commentService.deleteComment(commentId, session.getAttribute("loginMember").toString());
        return "redirect:/posts/"+postId;
    }

}
