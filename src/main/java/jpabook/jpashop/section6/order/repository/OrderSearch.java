package jpabook.jpashop.section6.order.repository;

import jakarta.validation.constraints.NotNull;
import jpabook.jpashop.section3.domain.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderSearch {

    @NotNull
    private final String memberName;
    @NotNull
    private final OrderStatus status;
}
