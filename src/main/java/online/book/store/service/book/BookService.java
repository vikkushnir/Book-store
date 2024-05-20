package online.book.store.service.book;

import java.util.List;
import online.book.store.dto.request.book.BookRequestDto;
import online.book.store.dto.request.book.BookSearchParametersDto;
import online.book.store.dto.response.book.BookDtoWithoutCategoryIds;
import online.book.store.dto.response.book.BookResponseDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponseDto save(BookRequestDto requestDto);

    List<BookResponseDto> getAll(Pageable pageable);

    BookResponseDto getById(Long id);

    BookResponseDto updateById(Long id, BookRequestDto requestDto);

    void deleteById(Long id);

    List<BookResponseDto> search(BookSearchParametersDto searchParametersDto, Pageable pageable);

    List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id, Pageable pageable);
}
