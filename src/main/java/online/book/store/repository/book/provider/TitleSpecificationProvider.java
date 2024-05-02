package online.book.store.repository.book.provider;

import online.book.store.dto.BookSearchParametersDto;
import online.book.store.model.Book;
import online.book.store.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    public static final String TITLE = "title";

    @Override
    public String getKey() {
        return TITLE;
    }

    public Specification<Book> getSpecification(BookSearchParametersDto bookSearchParametersDto) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(
                        root.get(TITLE), "%" + bookSearchParametersDto.title() + "%");

    }
}
