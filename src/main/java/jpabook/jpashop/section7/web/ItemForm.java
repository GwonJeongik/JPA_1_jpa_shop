package jpabook.jpashop.section7.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemForm {

    private String name;
    private int price;
    private int stockQuantity;

    private String isbn;
    private String author;
}
