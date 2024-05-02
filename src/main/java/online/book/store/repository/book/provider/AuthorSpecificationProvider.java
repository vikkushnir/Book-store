package online.book.store.repository.book.provider;

import java.util.Arrays;
import online.book.store.dto.BookSearchParametersDto;
import online.book.store.model.Book;
import online.book.store.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    public static final String AUTHOR = "author";

    @Override
    public String getKey() {
        return AUTHOR;
    }

    public Specification<Book> getSpecification(BookSearchParametersDto bookSearchParametersDto) {
        return (root, query, criteriaBuilder)
                -> root.get(AUTHOR)
                .in(Arrays.stream(bookSearchParametersDto.author()).toArray());
    }
}
