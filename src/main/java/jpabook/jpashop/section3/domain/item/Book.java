package jpabook.jpashop.section3.domain.item;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Book extends Item {

    private String isbn;
    private String author;

    public Book(String name, int price, int stockQuantity) {
        super(name, price, stockQuantity);
    }
}
