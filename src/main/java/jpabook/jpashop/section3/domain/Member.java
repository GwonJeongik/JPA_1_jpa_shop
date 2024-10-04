package jpabook.jpashop.section3.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Embedded
    private Address address;

    //Member를 저장, 삭제 할 때, Order을 전파시킬 이유가 없음. <- `Member`와 `Order`은 독립적인 생존주기를 가지는 게 자연스럽다.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    private String name;

    public Member(String name) {
        this.name = name;
    }
}
