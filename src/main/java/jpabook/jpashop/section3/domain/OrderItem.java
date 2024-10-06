package jpabook.jpashop.section3.domain;

import jakarta.persistence.*;
import jpabook.jpashop.section3.domain.item.Item;
import lombok.Getter;

@Entity
@Getter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    //주문 수량
    private int count;
    private int orderPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public void putOrder(Order order) {
        this.order = order;
    }

    //==생성 로직==//
    /* 상품 주문 생성 메서드 */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.item = item;
        orderItem.orderPrice = orderPrice;
        orderItem.count = count;

        //1. 상품 주문 생성할 때, 상품의 전체 재고 감소//
        item.removeStock(count);

        return orderItem;
    }

    //==비지니스 로직==//
    /* 취소된 상품 수량 원복 메서드 */
    public void cancel() {
        item.addStock(count);
    }

    //==조회 로직==//
    /* 주문 상품의 가격 조회 메서드 */
    public int getTotalPrice() {
        return orderPrice * count;
    }
}
