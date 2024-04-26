package online.book.store.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import online.book.store.dto.BookDto;
import online.book.store.dto.BookRequestDto;
import online.book.store.exception.EntityNotFoundException;
import online.book.store.mapper.BookMapper;
import online.book.store.model.Book;
import online.book.store.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(BookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find book by id: " + id)));
    }

    @Override
    public BookDto updateById(Long id, BookRequestDto requestDto) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find book with id: " + id);
        }
        Book book = bookMapper.toModel(requestDto);
        book.setId(id);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
