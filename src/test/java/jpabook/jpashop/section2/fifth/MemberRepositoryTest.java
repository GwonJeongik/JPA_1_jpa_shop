package jpabook.jpashop.section2.fifth;

import jpabook.jpashop.section3.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 5. JPA와 DB 설정, 동작확인
 * tdd -> setting -> Live Template에서 만들었다.
 * application.yml -> 계층 구조 -> 복잡할 때 사용하면 좋다.
 */
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    void testMember() {
        //given
        Member member = new Member();
//        member.setUsername("Test Member");

        //when
        Long saveMemberId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveMemberId);

        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
//        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        // JPA 엔티티 동일성 보장
        // [영속성 컨텍스트로 인해서 동일성이 보장된다.]
        assertThat(findMember).isEqualTo(member);
    }
}