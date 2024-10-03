package jpabook.jpashop.section3.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "A")
public class Album extends Item {

    private String artist;
    private String etc;
}
