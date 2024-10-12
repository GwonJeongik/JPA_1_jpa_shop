package jpabook.jpashop.section6.order.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jpabook.jpashop.section3.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
    public List<Order> findByString(OrderSearch orderSearch) {
        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        //주문 상태 검색//
        if (orderSearch.getOrderStatus() != null) {
            jpql += " where o.status = :status";
            isFirstCondition = false;
        }

        //회원 이름 검색//
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        //쿼리문 생성//
        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                //최대 1000건 조회//
                .setMaxResults(1000);

        //쿼리문에 파라미터 바인딩//
        if (orderSearch.getOrderStatus() != null) {
            query.setParameter("status", orderSearch.getOrderStatus());
        }

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }
}
