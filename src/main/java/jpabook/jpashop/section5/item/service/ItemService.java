package jpabook.jpashop.section5.item.service;

import jpabook.jpashop.section3.domain.item.Item;
import jpabook.jpashop.section5.UpdateDto;
import jpabook.jpashop.section5.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    /* 상품 등록 메서드 */
    @Transactional
    public Long saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    /* 상품 단일 조회 메서드 */
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    /* 상품 목록 조회 메서드 */
    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}
