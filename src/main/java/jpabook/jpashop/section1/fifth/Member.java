package jpabook.jpashop.section1.fifth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * 5. JPA와 DB 설정, 동작확인
 * tdd -> setting -> Live Template에서 만들었다.
 * application.yml -> 계층 구조 -> 복잡할 때 사용하면 좋다.
 */

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
}
