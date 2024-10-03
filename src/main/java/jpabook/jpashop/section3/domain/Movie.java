package jpabook.jpashop.section3.domain.item;

import jakarta.persistence.Entity;

@Entity
public class Movie extends jpabook.jpashop.section3.domain.item.Item {

    private String director;
    private String actor;
}
