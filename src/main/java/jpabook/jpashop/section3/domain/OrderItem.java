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

    public void changeOrder(Order order) {
        this.order = order;
    }
}
