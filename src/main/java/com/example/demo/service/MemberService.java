package com.example.demo.service;

import com.example.demo.controller.MemberForm;
import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    /**
     * 회원 조회
     */
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    public Member findOneWithNickName(String nickname) {
        return memberRepository.findByNickname(nickname)
                .orElse(null);
    }

    /**
     * 회원 수정 -> 비밀번호
     */
    @Transactional
    public void update(Long id, String password) {
        Member member = memberRepository.findOne(id);
        member.updateMember(password);
    }

    public boolean login(MemberForm form) {
        Member dbMember = memberRepository.findByNickname(form.getNickname())
                .filter(m -> m.getPassword().equals(form.getPassword()))
                .orElse(null);
        return dbMember != null;
    }

    /**
     * 회원 로그인 세션 확인
     */
    public boolean isLogin(HttpSession session) {
        Object attribute = session.getAttribute("loginMember");
        if(attribute != null) {
            String loginNickname = attribute.toString();
            return memberRepository.findByNickname(loginNickname).isPresent();
        }
        return false;
    }
}
