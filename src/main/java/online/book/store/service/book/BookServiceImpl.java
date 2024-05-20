package online.book.store.service.book;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import online.book.store.dto.request.book.BookRequestDto;
import online.book.store.dto.request.book.BookSearchParametersDto;
import online.book.store.dto.response.book.BookDtoWithoutCategoryIds;
import online.book.store.dto.response.book.BookResponseDto;
import online.book.store.exception.EntityNotFoundException;
import online.book.store.mapper.BookMapper;
import online.book.store.model.Book;
import online.book.store.model.Category;
import online.book.store.repository.book.BookRepository;
import online.book.store.repository.book.spec.BookSpecificationBuilder;
import online.book.store.repository.category.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    public BookResponseDto save(BookRequestDto requestDto) {
        Set<Long> categoryIds = requestDto.getCategoryIds();
        List<Category> categories = categoryRepository.findAllById(categoryIds);

        if (categories.size() != categoryIds.size()) {
            Set<Long> foundCategoryIds = categories.stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet());
            categoryIds.removeAll(foundCategoryIds);
            throw new EntityNotFoundException("Invalid category ID: " + categoryIds);
        }

        Book book = bookMapper.toModel(requestDto);
        book.setCategories(new HashSet<>(categories));

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookResponseDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookResponseDto getById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find book by id: " + id)));
    }

    @Override
    public BookResponseDto updateById(Long id, BookRequestDto requestDto) {
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

    @Override
    public List<BookResponseDto> search(BookSearchParametersDto searchParametersDto,
                                        Pageable pageable
    ) {
        Specification<Book> bookSpecification =
                bookSpecificationBuilder.build(searchParametersDto);
        return bookRepository.findAll(bookSpecification, pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id, Pageable pageable) {
        return bookRepository.findBooksByCategoryId(id, pageable).stream()
                .map(bookMapper::toDtoWithoutCategoryIds)
                .toList();
    }
}
