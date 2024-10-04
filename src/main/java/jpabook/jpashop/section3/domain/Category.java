package jpabook.jpashop.section3.domain;

import jakarta.persistence.*;
import jpabook.jpashop.section3.domain.item.Item;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item", joinColumns =
    @JoinColumn(name = "catefory_id"), inverseJoinColumns =
    @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    /* Category 연관관계 편의 메서드 */
    public void addChildCategory(Category child) {
        this.child.add(child);

        //같은 클래스 내에서는 다른 인스턴스의 `private` 필드도 접근 가능하다.
        child.parent = this;
    }
}
