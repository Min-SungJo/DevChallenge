package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/join")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "/members/createMemberForm";
    }

    @PostMapping("/members/join")
    public String create(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "/members/createMemberForm";
        }
        Member member = new Member();
        member.setNickname(form.getNickname());
        member.setPassword(form.getPassword());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members/login")
    public String loginForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "/members/loginMemberForm";
    }

    @PostMapping("/members/login")
    public String login(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "/members/loginMemberForm";
        }
        if (memberService.login(form)) {
            return "redirect:/";
        } else{
            return "/members/loginMemberForm";
        }
    }
}
