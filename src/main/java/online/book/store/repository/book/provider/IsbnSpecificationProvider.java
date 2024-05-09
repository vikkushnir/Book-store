package online.book.store.repository.book.provider;

import online.book.store.dto.request.book.BookSearchParametersDto;
import online.book.store.model.Book;
import online.book.store.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    public static final String ISBN = "isbn";

    @Override
    public String getKey() {
        return ISBN;
    }

    public Specification<Book> getSpecification(BookSearchParametersDto bookSearchParametersDto) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get(ISBN), bookSearchParametersDto.isbn());

    }
}
