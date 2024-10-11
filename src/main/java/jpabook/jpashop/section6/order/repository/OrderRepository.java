package jpabook.jpashop.section6.order.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.section3.domain.Order;
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
    public List<Order> findAll(OrderSearch orderSearch) {
        return em.createQuery("select o from Order o join o.member m", Order.class)
                .getResultList();
    }
}
