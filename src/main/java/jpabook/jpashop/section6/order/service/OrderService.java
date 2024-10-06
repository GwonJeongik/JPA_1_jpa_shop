package jpabook.jpashop.section6.order.service;

import jpabook.jpashop.section3.domain.Order;
import jpabook.jpashop.section6.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    /* 주문 생성 */
    @Transactional
    public Long crateOrder(Order order) {
        orderRepository.save(order);
        return order.getId();
    }

    /* 주문 내역 조회 */
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    /* 주문 삭제 */
    @Transactional
    public void delete(Long id) {
        orderRepository.delete(id);
    }
}
