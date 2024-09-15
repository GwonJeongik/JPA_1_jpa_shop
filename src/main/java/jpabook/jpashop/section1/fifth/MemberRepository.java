package jpabook.jpashop.section1.fifth;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 5. JPA와 DB 설정, 동작확인
 * tdd -> setting -> Live Template에서 만들었다.
 * application.yml -> 계층 구조 -> 복잡할 때 사용하면 좋다.
 */
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
