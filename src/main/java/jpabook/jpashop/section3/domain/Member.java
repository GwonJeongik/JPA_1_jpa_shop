package jpabook.jpashop.section3.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @Embedded
    private Address address;

    //Member를 저장, 삭제 할 때, Order을 전파시킬 이유가 없음. <- `Member`와 `Order`은 독립적인 생존주기를 가지는 게 자연스럽다.
    @OneToMany(mappedBy = "member")
    private final List<Order> orders = new ArrayList<>();

    public Member(String name) {
        this.name = name;
    }
}
