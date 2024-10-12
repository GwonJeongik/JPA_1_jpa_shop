package jpabook.jpashop.section3.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    /* Order 변경 메서드 */
    public void changeOrder(Order order) {
        this.order = order;
    }

    /* address 변경 메서드 */
    public void changeAddress(Address address) {
        this.address = address;
    }

    /* delivery status 변경 메서드 */
    public void changeStatus(DeliveryStatus status) {
        this.status = status;
    }

}
