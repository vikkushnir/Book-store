package online.book.store.repository.book.spec;

import lombok.RequiredArgsConstructor;
import online.book.store.dto.request.book.BookSearchParametersDto;
import online.book.store.model.Book;
import online.book.store.repository.book.provider.AuthorSpecificationProvider;
import online.book.store.repository.book.provider.IsbnSpecificationProvider;
import online.book.store.repository.book.provider.PriceFromSpecificationProvider;
import online.book.store.repository.book.provider.PriceToSpecificationProvider;
import online.book.store.repository.book.provider.TitleSpecificationProvider;
import online.book.store.repository.specification.SpecificationBuilder;
import online.book.store.repository.specification.SpecificationProviderManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParametersDto) {
        Specification<Book> spec = Specification.where(null);
        if (isNotNullOrEmpty(searchParametersDto.author())) {
            spec = spec.and(buildSpecification(AuthorSpecificationProvider.AUTHOR,
                    searchParametersDto));
        }
        if (StringUtils.isNotEmpty(searchParametersDto.title())) {
            spec = (buildSpecification(TitleSpecificationProvider.TITLE,
                    searchParametersDto));
        }
        if (StringUtils.isNotEmpty(searchParametersDto.isbn())) {
            spec = spec.and(buildSpecification(IsbnSpecificationProvider.ISBN,
                    searchParametersDto));
        }
        if (StringUtils.isNotEmpty(searchParametersDto.priceFrom())) {
            spec = spec.and(buildSpecification(PriceFromSpecificationProvider.PRICE_FROM,
                    searchParametersDto));
        }
        if (StringUtils.isNotEmpty(searchParametersDto.priceTo())) {
            spec = spec.and(buildSpecification(PriceToSpecificationProvider.PRICE_TO,
                    searchParametersDto));
        }
        return spec;
    }

    private boolean isNotNullOrEmpty(String[] value) {
        return value != null && value.length > 0;
    }

    private Specification<Book> buildSpecification(
            String key,
            BookSearchParametersDto searchParametersDto
    ) {
        return bookSpecificationProviderManager
                .getSpecificationProvider(key)
                .getSpecification(searchParametersDto);
    }
}
