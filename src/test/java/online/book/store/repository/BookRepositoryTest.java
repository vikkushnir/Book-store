package online.book.store.repository;

import java.util.List;
import online.book.store.model.Book;
import online.book.store.repository.book.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
public class BookRepositoryTest {
    private static final Long CATEGORY_ID = 5L;
    private static final int EXPECTED = 3;
    private static final Long CATEGORY_ID_INVALID = 10L;
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 5;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Find all books by category ID: result no books")
    public void findBooksByCategoryId_InvalidId_ReturnNoBooks() {
        List<Book> actual = bookRepository.findBooksByCategoryId(
                CATEGORY_ID_INVALID,
                PageRequest.of(PAGE_NUMBER, PAGE_SIZE)
        ).getContent();
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("Find all books by category ID: result all books")
    public void findBooksByCategoryId_ValidId_ReturnAllBooks() {
        Page<Book> actual = bookRepository.findBooksByCategoryId(
                CATEGORY_ID,
                PageRequest.of(PAGE_NUMBER, PAGE_SIZE)
        );
        Assertions.assertEquals(EXPECTED, actual.getTotalElements());
    }
}
