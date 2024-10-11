package jpabook.jpashop.section5.item.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.section3.domain.item.Item;
import jpabook.jpashop.section5.UpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    @PersistenceContext
    private final EntityManager em;

    /* 상품 등록 메서드 */
    public void save(Item item) {
        //==DB에 등록된 상품이 없으면 persist()==//
        if (item.getId() == null) {
            em.persist(item);
            //==DB에 등록된 상품이 있으면 merge()==//
        } else {
            em.merge(item);
        }
    }

    /* 상품 단일 조회 메서드 */
    public Item findOne(Long itemId) {
        return em.find(Item.class, itemId);
    }

    /* 상품 목록 조회 메서드 */
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
