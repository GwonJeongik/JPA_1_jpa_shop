package jpabook.jpashop.section5;

import jpabook.jpashop.section7.web.ItemForm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class UpdateItemDto {

    private final String name;
    private final int price;
    private final int stockQuantity;

    private final String isbn;
    private final String author;

    public UpdateItemDto(ItemForm form) {
        this.name = form.getName();
        this.price = form.getPrice();
        this.stockQuantity = form.getStockQuantity();
        this.isbn = form.getIsbn();
        this.author = form.getAuthor();
    }
}
