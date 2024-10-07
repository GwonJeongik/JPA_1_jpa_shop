package jpabook.jpashop.section3.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private LocalDateTime orderDate;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    /* 주문 상태 변경 메서드 */
    public void changeStatus(OrderStatus status) {
        this.status = status;
    }

    /* Member 연관관계 편의 메서드 */
    public void changeMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    /* OrderItem 연관관계 편의 메서드 */
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.putOrder(this);
    }

    /* Delivery 연관관계 편의 메서드 */
    public void changeDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.changeOrder(this);
    }

    //=== 생성 로직 ===//

    /**
     * 주문 생성 메서드
     *
     * @param member     생성할 주문의 회원
     * @param delivery   주문의 상태
     * @param orderItems 주문할 상품들
     * @return 생성된 주문
     */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        //같은 클래스 안에서 다른 인스턴스 필드에 직접 접근 가능//
        Order order = new Order();
        order.member = member;
        order.delivery = delivery;

        for (OrderItem orderitem : orderItems) {
            order.addOrderItem(orderitem);
        }

        order.status = OrderStatus.ORDER;
        order.orderDate = LocalDateTime.now();

        return order;
    }

    //=== 비지니스 로직 ===//

    /**
     * 주문 취소
     */
    public void cancel() {
        //1. 배송 완료 상태 검증
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("배송이 완료된 상품은 취소가 불가능합니다.");
        }

        //2. 주문 상태 취소로 변경
        this.changeStatus(OrderStatus.CANCEL);

        //3. 취소된 상품 수량 원복
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //=== 조회 로직 ===//
    /* 전체 주문 가격 조회 메서드*/
    public int getTotalPrice() {
        int totalPrice = 0;
        //1. 각 상품의 주문한 가격을 더함.//
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
