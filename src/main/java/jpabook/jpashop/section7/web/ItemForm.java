package jpabook.jpashop.section7.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ItemForm {

    private final String name;
    private final int price;
    private final int stockQuantity;

    private final String isbn;
    private final String author;
}
