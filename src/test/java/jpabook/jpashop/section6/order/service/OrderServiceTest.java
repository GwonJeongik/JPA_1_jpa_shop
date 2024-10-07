package jpabook.jpashop.section6.order.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.section3.domain.Address;
import jpabook.jpashop.section3.domain.Member;
import jpabook.jpashop.section3.domain.Order;
import jpabook.jpashop.section3.domain.item.Book;
import jpabook.jpashop.section3.domain.item.Item;
import jpabook.jpashop.section3.domain.item.NotEnoughStockQuantity;
import jpabook.jpashop.section6.order.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static jpabook.jpashop.section3.domain.OrderStatus.CANCEL;
import static jpabook.jpashop.section3.domain.OrderStatus.ORDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 테스트 요구사항
 * 상품 주문이 성공해야 한다.
 * 상품을 주문할 때 재고 수량을 초과하면 안 된다.
 * 주문 취소가 성공해야 한다
 */
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName(value = "상품 주문이 성공한다.")
    void order_success() {
        //given
        Member member = createMember();
        em.persist(member);

        Item book = createBook("JPA", 10000, 10);
        em.persist(book);

        int orderCount = 2;

        //when
        Long orderId = orderService.createOrder(member.getId(), book.getId(), orderCount);
        Order findOrder = orderRepository.findOne(orderId);

        //then
        assertThat(findOrder.getStatus()).isEqualTo(ORDER); // 주문 상태 확인
        assertThat(findOrder.getOrderItems().size()).isEqualTo(1); //orderItems 확인
        assertThat(findOrder.getTotalPrice()).isEqualTo(book.getPrice() * orderCount); // 주문 금액 확인
        assertThat(book.getStockQuantity()).isEqualTo(8); // 상품 재고 확인
    }

    @Test
    @DisplayName(value = "상품을 주문할 때, 재고 수량을 초과하면 안 된다.")
    void not_enough_stock() {
        //given
        Member member = createMember();
        Item book = createBook("jpa", 10000, 10);

        int orderCount = 11;

        //when & then
        assertThatThrownBy(() -> orderService.createOrder(member.getId(), book.getId(), orderCount))
                .isInstanceOf(NotEnoughStockQuantity.class);
    }

    @Test
    @DisplayName(value = "주문 취소가 성공한다.")
    void order_cancel() {
        //given
        Member member = createMember();
        Item book = createBook("jpa", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.createOrder(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);
        Order order = orderRepository.findOne(orderId);

        //then
        assertThat(order.getStatus()).isEqualTo(CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);
    }

    private Member createMember() {
        Member member = new Member("진성진", new Address("서울", "경리단길", "12345"));
        em.persist(member);
        return member;
    }

    private Item createBook(String name, int price, int stockQuantity) {
        Book book = new Book(name, price, stockQuantity);
        em.persist(book);
        return book;
    }
}