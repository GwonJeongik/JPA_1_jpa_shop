package jpabook.jpashop.section4.service;

import jpabook.jpashop.section3.domain.Member;
import jpabook.jpashop.section4.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;

    /* 회원 가입 */
    @Transactional // 데이터 변경
    public Long join(Member member) {
        validateDuplicateMember(member);

        repository.save(member);
        return member.getId();
    }

    /* 회원 하나 조회 */
    public Member findOne(Long memberId) {
        return repository.findOne(memberId);
    }
    /* 모든 회원 조회*/

    public List<Member> findMembers() {
        return repository.findAll();
    }

    /* 중복 회원 검증 */
    private void validateDuplicateMember(Member member) {
        List<Member> members = repository.findByName(member.getName());

        if (!members.isEmpty()) {
            throw new IllegalStateException("중복된 회원이 있습니다.");
        }
    }
}
