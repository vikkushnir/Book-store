package online.book.store.repository.specification;

import online.book.store.dto.request.book.BookSearchParametersDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    String getKey();

    Specification<T> getSpecification(BookSearchParametersDto bookSearchParametersDto);
}
