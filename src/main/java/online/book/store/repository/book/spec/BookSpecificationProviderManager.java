package online.book.store.repository.book.spec;

import java.util.List;
import lombok.RequiredArgsConstructor;
import online.book.store.model.Book;
import online.book.store.repository.specification.SpecificationProvider;
import online.book.store.repository.specification.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(provider -> provider.getKey().equals(key))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Can't find specification provider "
                                + "for key: " + key));
    }
}
