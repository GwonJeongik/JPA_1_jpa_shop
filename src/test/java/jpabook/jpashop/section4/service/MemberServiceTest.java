package jpabook.jpashop.section4.service;

import jpabook.jpashop.section3.domain.Member;
import jpabook.jpashop.section4.member.repository.MemberRepository;
import jpabook.jpashop.section4.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName(value = "회원가입 정상")
    void join() {
        //given
        Member member = new Member("정우형");

        //when
        Long saveId = memberService.join(member);
        Member findMember = memberService.findOne(saveId);

        //then
        assertThat(findMember).isEqualTo(member); // JPA 영속성 컨텍스트 동일성 보장
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
    }

    @Test
    @DisplayName(value = "회원가입 중복 회원 예외")
    void joinSameNameException() {
        //given
        Member member1 = new Member("정우형");
        Member member2 = new Member("정우형");

        memberService.join(member1);

        //when & then
        assertThatThrownBy(() -> memberService.join(member2))
                .isInstanceOf(IllegalStateException.class);
    }
}