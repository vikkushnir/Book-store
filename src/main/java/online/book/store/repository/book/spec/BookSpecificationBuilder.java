package online.book.store.repository.book.spec;

import lombok.RequiredArgsConstructor;
import online.book.store.dto.BookSearchParametersDto;
import online.book.store.model.Book;
import online.book.store.repository.specification.SpecificationBuilder;
import online.book.store.repository.specification.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    public static final String AUTHOR = "author";
    public static final String TITLE = "title";
    public static final String ISBN = "isbn";
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParametersDto) {
        Specification<Book> spec = Specification.where(null);
        if (isNotNullOrEmpty(searchParametersDto.author())) {
            spec = buildSpecification(AUTHOR, searchParametersDto.author());
        }
        if (isNotNullOrEmpty(searchParametersDto.title())) {
            spec = buildSpecification(TITLE, searchParametersDto.title());
        }
        if (isNotNullOrEmpty(searchParametersDto.isbn())) {
            spec = buildSpecification(ISBN, searchParametersDto.isbn());
        }
        return spec;
    }

    private boolean isNotNullOrEmpty(String value) {
        return value != null && !value.isEmpty();
    }
    
    private Specification<Book> buildSpecification(String key, String value) {
        return bookSpecificationProviderManager
                .getSpecificationProvider(key)
                .getSpecification(value);
    }
}
