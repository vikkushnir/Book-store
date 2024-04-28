package online.book.store.service;

import java.util.List;
import online.book.store.dto.BookDto;
import online.book.store.dto.BookRequestDto;
import online.book.store.dto.BookSearchParametersDto;

public interface BookService {
    BookDto save(BookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto updateById(Long id, BookRequestDto requestDto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParametersDto searchParametersDto);
}
