package com.example.demo.service;

import com.example.demo.controller.MemberForm;
import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
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
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    /**
     * 회원 수정
     */
    @Transactional
    public void update(Long id, String nickname) {
        Member member = memberRepository.findOne(id);
        member.setNickname(nickname);
    }

    public boolean login(MemberForm form) {
        Member dbMember = memberRepository.findByNickname(form.getNickname());
        if(!form.getNickname().equals(dbMember.getNickname())) return false;
        if(!form.getPassword().equals(dbMember.getPassword())) return false;
        return true;
    }
}
