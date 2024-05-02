package online.book.store.repository.book.provider;

import java.math.BigDecimal;
import online.book.store.dto.BookSearchParametersDto;
import online.book.store.model.Book;
import online.book.store.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PriceFromSpecificationProvider implements SpecificationProvider<Book> {
    public static final String PRICE_FROM = "priceFrom";
    private static final String PRICE_COLUMN = "price";

    @Override
    public String getKey() {
        return PRICE_FROM;
    }

    @Override
    public Specification<Book> getSpecification(BookSearchParametersDto bookSearchParametersDto) {
        BigDecimal minPrice = new BigDecimal(bookSearchParametersDto.priceFrom());
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.greaterThanOrEqualTo(root.get(PRICE_COLUMN), minPrice);
    }
}
