package online.book.store.repository.book.provider;

import online.book.store.model.Book;
import online.book.store.repository.book.spec.BookSpecificationBuilder;
import online.book.store.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {

    @Override
    public String getKey() {
        return BookSpecificationBuilder.AUTHOR;
    }

    public Specification<Book> getSpecification(String param) {
        return (root, query, criteriaBuilder)
                -> root.get(BookSpecificationBuilder.AUTHOR).in(param);
    }
}
