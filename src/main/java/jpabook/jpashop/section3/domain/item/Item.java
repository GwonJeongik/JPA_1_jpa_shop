package jpabook.jpashop.section3.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.section3.domain.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    private String name;
    private int price;
    private int stockQuantity;

    public Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    /* 상품 수정 메서드 */
    public void updateItem(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    /* 재고 증가 메서드 */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /* 재고 감소 메서드 */
    public void removeStock(int quantity) {
        int result = this.stockQuantity - quantity;

        //==수정된 재고 마이너스 검증==//
        if (result < 0) {
            throw new NotEnoughStockQuantity("need more stock");
        }

        this.stockQuantity = result;
    }
}
