package jpabook.jpashop.section6.order.repository;

import jpabook.jpashop.section3.domain.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderSearch {

    private final String memberName;
    private final OrderStatus orderStatus;
}
