package jpabook.jpashop.section3.domain.item;

import jakarta.persistence.Entity;
import jpabook.jpashop.section7.web.BookForm;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Book extends Item {

    private String isbn;
    private String author;

    public Book(String name, int price, int stockQuantity) {
        super(name, price, stockQuantity);
    }

    /**
     * Book 객체 생성 메서드 - BookForm을 이용
     *
     * @param bookForm DTO
     * @return Book 객체
     */
    public static Book createBook(BookForm bookForm) {
        Book book = new Book(bookForm.getName(), bookForm.getPrice(), bookForm.getStockQuantity());
        book.author = bookForm.getAuthor();
        book.isbn = bookForm.getIsbn();
        return book;
    }

    /**
     * Book 필드 수정 메서드
     *
     * @param bookForm
     */
    public void updateBook(BookForm bookForm) {
        this.updateItem(bookForm.getName(), bookForm.getPrice(), bookForm.getStockQuantity());
        this.author = bookForm.getAuthor();
        this.isbn = bookForm.getIsbn();
    }
}
