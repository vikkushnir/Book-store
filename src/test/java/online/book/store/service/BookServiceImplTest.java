package online.book.store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import online.book.store.dto.response.book.BookResponseDto;
import online.book.store.exception.EntityNotFoundException;
import online.book.store.mapper.BookMapper;
import online.book.store.model.Book;
import online.book.store.repository.book.BookRepository;
import online.book.store.service.book.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 100L;
    private static final String TITLE = "Book title";
    private static final String AUTHOR = "Author Bob";
    private static final String ISBN = "9781234567897";
    private static final BigDecimal PRICE = BigDecimal.valueOf(10.99);
    private static final String DESCRIPTION = "Description book";
    private static final String COVER_IMAGE = "http://coverImage.com";
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 10;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Get all books")
    public void getAll_ValidPageable_ReturnAllBooks() {
        Book book = defaultBook();
        book.setId(VALID_ID);

        BookResponseDto bookDto = toDto(book);

        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        List<Book> bookList = List.of(book);
        Page<Book> bookPage = new PageImpl<>(bookList, pageable, bookList.size());

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        List<BookResponseDto> actual = bookService.getAll(pageable);

        assertEquals(bookList.size(), actual.size());
    }

    @Test
    @DisplayName("Get book by valid id")
    public void getBookById_ValidBookId_ReturnValidBookDto() {
        Book book = defaultBook();
        book.setId(VALID_ID);

        BookResponseDto bookDto = toDto(book);

        when(bookRepository.findById(VALID_ID)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookResponseDto actual = bookService.getById(VALID_ID);

        assertEquals(book.getId(), actual.getId());
    }

    @Test
    @DisplayName("Get book by invalid id")
    public void getBookById_InvalidBookId_NotOk() {
        when(bookRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> bookService.getById(INVALID_ID));
    }

    private Book defaultBook() {
        Book book = new Book();
        book.setId(VALID_ID);
        book.setTitle(TITLE);
        book.setAuthor(AUTHOR);
        book.setIsbn(ISBN);
        book.setPrice(PRICE);
        book.setDescription(DESCRIPTION);
        book.setCoverImage(COVER_IMAGE);
        book.setCategories(Set.of());
        return book;
    }

    private BookResponseDto toDto(Book book) {
        BookResponseDto bookDto = new BookResponseDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPrice(book.getPrice());
        bookDto.setDescription(book.getDescription());
        bookDto.setCoverImage(book.getCoverImage());
        bookDto.setCategoryIds(Set.of());
        return bookDto;
    }
}
