package jpabook.jpashop.section7.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.section3.domain.item.Book;
import jpabook.jpashop.section3.domain.item.Item;
import jpabook.jpashop.section5.item.service.ItemService;
import jpabook.jpashop.section7.web.BookForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /**
     * 상품 등록 폼
     *
     * @param model 빈 BookForm 객체를 넘겨준다.
     * @return 상품 생성 화면 html 이동
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    /**
     * 상품 생성
     *
     * @param bookForm      id, price, stockQuantity, name, author, isbn
     * @param bindingResult BookForm 검증 결과 오류를 담음.
     * @return 상품 목록으로 이동
     */
    @PostMapping("/new")
    public String create(@Valid BookForm bookForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "items/createItemForm";
        }

        Book book = Book.createBook(bookForm);
        itemService.saveItem(book);

        return "redirect:/items";
    }

    /**
     * 상품 목록 조회
     *
     * @param model 조회한 상품 목록을 담아준다.
     * @return 상품 목록으로 이동
     */
    @GetMapping
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    /**
     * 상품 수정 폼
     *
     * @param model
     * @return items/updateItemForm
     */
    @GetMapping("/{itemId}/edit")
    public String updateForm(@PathVariable(value = "itemId") Long itemId, Model model) {
        Item item = itemService.findOne(itemId);

        if (item instanceof Book book) {
            BookForm bookForm = new BookForm();
            bookForm.UseBook(book);
            model.addAttribute("form", bookForm);
        }

        return "items/updateItemForm";
    }

    /**
     * 상품 수정
     *
     * @param bookForm      id, price, stockQuantity, name, author, isbn
     * @param bindingResult
     * @return redirect:/items
     */
    @PostMapping("/{itemId}/edit")
    public String update(
            @PathVariable(value = "itemId") Long itemId,
            @Valid @ModelAttribute(value = "form") BookForm bookForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "items/updateItemForm";
        }

        Item item = itemService.findOne(itemId);

        if (item instanceof Book book) {
            book.updateBook(bookForm);
        }

        itemService.saveItem(item);

        return "redirect:/items";
    }
}
