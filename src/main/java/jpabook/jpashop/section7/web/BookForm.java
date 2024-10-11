package jpabook.jpashop.section7.web;

import jpabook.jpashop.section3.domain.item.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookForm {

    private Long id;

    private int price;
    private int stockQuantity;

    private String name;
    private String author;
    private String isbn;

    public void UseBook(Book book) {
        this.id = book.getId();
        this.price = book.getPrice();
        this.stockQuantity = book.getStockQuantity();
        this.name = book.getName();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
    }

}
