package jpabook.jpashop.section7.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.section3.domain.Address;
import jpabook.jpashop.section3.domain.Member;
import jpabook.jpashop.section4.member.service.MemberService;
import jpabook.jpashop.section7.web.MemberForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 가입
     *
     * @param model 빈 깡통 모델
     * @return 회원 가입 화면으로 이동
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    /**
     * 회원 가입
     *
     * @param memberForm    회원 DTO
     * @param bindingResult `@Valid memberForm`의 유효성 검사에서 발생한 예외를 담는 통.
     * @return 홈 화면으로 redirect
     */
    @PostMapping("/new")
    public String create(@Valid MemberForm memberForm, BindingResult bindingResult) {

        log.info("memberForm={}", memberForm);
        if (bindingResult.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipCode());
        Member member = new Member(memberForm.getName(), address);

        memberService.join(member);

        return "redirect:/";
    }

    /**
     * 회원 목록 조회
     *
     * @param model 조회한 목록 담아줌
     * @return 회원 목록 조회 html
     */
    @GetMapping
    public String list(Model model) {
        List<MemberForm> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
