package jpabook.jpashop.section6.order.service;

import jpabook.jpashop.section2.fifth.MemberRepository;
import jpabook.jpashop.section3.domain.*;
import jpabook.jpashop.section3.domain.item.Item;
import jpabook.jpashop.section5.item.repository.ItemRepository;
import jpabook.jpashop.section6.order.repository.OrderRepository;
import jpabook.jpashop.section6.order.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /* 주문 생성 */
    @Transactional
    public Long createOrder(Long memberId, Long itemId, int count) {
        // 1. 엔티티 조회 //
        Member member = memberRepository.find(memberId);
        Item item = itemRepository.findOne(itemId);

        // 2. 주문 상품 생성 //
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 3. 배송 정보 생성 //
        Delivery delivery = new Delivery();
        delivery.changeAddress(member.getAddress());
        delivery.changeStatus(DeliveryStatus.READY);

        // 4. 주문 생성 //
        Order order = Order.createOrder(member, delivery, orderItem);

        // 5. 주문 저장 //
        orderRepository.save(order);
        return order.getId();
    }

    /* 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 1. 주문 엔티티 조회 //
        Order order = orderRepository.findOne(orderId);

        // 2. 주문 취소 //
        order.cancel();
    }

    /* 주문 목록 조회 */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findByString(orderSearch);
    }
}
