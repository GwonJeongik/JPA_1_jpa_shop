package jpabook.jpashop.section6.order.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.section3.domain.Order;
import jpabook.jpashop.section3.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    /* 주문 생성 */
    public void save(Order order) {
        em.persist(order);
    }

    /* 주문 단건 조회 */
    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

    /* 주문 내역 조회 */
    public List<Order> findAll(Long memberId, OrderStatus status) {
        return em.createQuery("select o from Order o where o.member.id = :memberId and o.status = :status", Order.class)
                .setParameter("memberId", memberId)
                .setParameter("status", status)
                .getResultList();
    }
}
