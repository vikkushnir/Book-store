package online.book.store.repository.book.provider;

import online.book.store.model.Book;
import online.book.store.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    private static final String ISBN = "isbn";

    @Override
    public String getKey() {
        return ISBN;
    }

    public Specification<Book> getSpecification(String param) {
        return (root, query, criteriaBuilder) -> root.get(ISBN).in(param);

    }
}
