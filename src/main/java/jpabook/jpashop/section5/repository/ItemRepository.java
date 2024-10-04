package jpabook.jpashop.section5.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    @PersistenceContext
    private final EntityManager em;

    /* 상품 등록 */
    public void save() {

    }

    /* 상품 목록 조회 */
    public void findAll() {

    }

    /* 상품 수정 */
    public void update() {

    }
}
