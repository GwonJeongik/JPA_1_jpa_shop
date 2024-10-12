package jpabook.jpashop.section7.controller;

import jpabook.jpashop.section3.domain.Order;
import jpabook.jpashop.section3.domain.item.Item;
import jpabook.jpashop.section4.member.service.MemberService;
import jpabook.jpashop.section5.item.service.ItemService;
import jpabook.jpashop.section6.order.repository.OrderSearch;
import jpabook.jpashop.section6.order.service.OrderService;
import jpabook.jpashop.section7.web.MemberForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    /**
     * 주문 생성 폼
     *
     * @param model
     * @return
     */
    @GetMapping
    public String createOrderForm(Model model) {
        List<MemberForm> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    /**
     * 주문 생성
     *
     * @param memberId 회원 아이디
     * @param itemId   상품 아이디
     * @param count    주문 수량
     * @return redirect:/orders
     */
    @PostMapping
    public String createOrder(
            @RequestParam(value = "memberId") Long memberId,
            @RequestParam(value = "itemId") Long itemId,
            @RequestParam(value = "count") int count
    ) {
        orderService.createOrder(memberId, itemId, count);
        return "redirect:/order/orders";
    }

    /**
     * 주문 목록 조회
     *
     * @param orderSearch
     * @param model
     * @return
     */
    @GetMapping("/orders")
    public String findOrder(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        log.info("절대경로 /orders로 진입");
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    /**
     * 주문 취소
     *
     * @param orderId
     * @return
     */

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/order/orders";
    }
}
