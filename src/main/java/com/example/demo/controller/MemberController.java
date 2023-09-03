package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;
import jakarta.servlet.http.HttpSession;
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
        Member member = Member.createMember(form.getNickname(), form.getPassword());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members/login")
    public String loginForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "/members/loginMemberForm";
    }

    @PostMapping("/members/login")
    public String login(@Valid MemberForm form, BindingResult result, HttpSession session) {
        if (result.hasErrors()) { // 빈 값이 있는가
            return "/members/loginMemberForm";
        }
        if (memberService.login(form)) {
            session.setAttribute("loginMember", form.getNickname());

            String dest = (String) session.getAttribute("dest");
            System.out.println("dest = " + dest);
            String redirect = (dest == null) ? "/" : dest;
            session.removeAttribute("dest");
            return "redirect:" + redirect;
        } else { // 로그인 실패
            result.reject("loginFail", "아이디 또는 비밀번호를 확인하여 주십시오.");
            return "/members/loginMemberForm";
        }
    }

    @GetMapping("/members/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginMember");
        return "redirect:/";
    }

}
