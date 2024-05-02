package online.book.store.repository.book.provider;

import java.math.BigDecimal;
import online.book.store.dto.BookSearchParametersDto;
import online.book.store.model.Book;
import online.book.store.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PriceToSpecificationProvider implements SpecificationProvider<Book> {
    public static final String PRICE_TO = "priceTo";
    private static final String PRICE_COLUMN = "price";

    @Override
    public String getKey() {
        return PRICE_TO;
    }

    @Override
    public Specification<Book> getSpecification(BookSearchParametersDto bookSearchParametersDto) {
        BigDecimal maxPrice = new BigDecimal(bookSearchParametersDto.priceTo());
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.lessThanOrEqualTo(root.get(PRICE_COLUMN), maxPrice);
    }
}
