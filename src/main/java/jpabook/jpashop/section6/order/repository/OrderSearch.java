package jpabook.jpashop.section6.order.repository;

import jakarta.validation.constraints.NotNull;
import jpabook.jpashop.section3.domain.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderSearch {

    @NotNull
    private final Long memberId;
    @NotNull
    private final OrderStatus status;
}
