package jpabook.jpashop.section3.domain.item;

import jakarta.persistence.Entity;

@Entity
public class Book extends Item {

    private String isbn;
    private String author;
}
