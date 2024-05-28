package online.book.store.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import online.book.store.dto.response.book.BookResponseDto;
import online.book.store.service.book.BookService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    private static final Long BOOK_ID = 1L;
    private static final String BOOK_TITLE = "some title";
    private static final String BOOK_URL = "/books";
    private static final String BOOK_BY_ID_URL = "/books/1";

    private static MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Return all books")
    void findAll_GetAllBooks_ReturnsAllBooks() throws Exception {
        List<BookResponseDto> bookDtoList = List.of(new BookResponseDto());
        when(bookService.getAll(any(Pageable.class))).thenReturn(bookDtoList);

        mockMvc.perform(get(BOOK_URL))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Return book by id")
    void getBookById_GetSpecificBook_ReturnBookById() throws Exception {
        BookResponseDto bookDto = new BookResponseDto();
        bookDto.setId(BOOK_ID);
        bookDto.setTitle(BOOK_TITLE);

        when(bookService.getById(BOOK_ID)).thenReturn(bookDto);

        mockMvc.perform(get(BOOK_BY_ID_URL))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete book by Admin")
    void deleteBook_WithAdminRole_Success() throws Exception {

        mockMvc.perform(delete(BOOK_BY_ID_URL))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Delete book by User")
    void deleteBook_WithUserRole_GetForbidden() throws Exception {

        mockMvc.perform(delete(BOOK_BY_ID_URL))
                .andExpect(status().isForbidden());
    }
}
