package jpabook.jpashop.section4.member.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.section3.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    /* 멤버 저장 */
    public void save(Member member) {
        em.persist(member);
    }

    /* 아이디로 멤버 조회 */
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    /* 이름으로 멤버 조회 */
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name=:name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    /* 모든 멤버 조회 */
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    /* 멤버 삭제 */
    public void delete(Long id) {
        Member member = em.find(Member.class, id);
        em.remove(member);
    }
}
