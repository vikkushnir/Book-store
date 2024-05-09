package online.book.store.dto.request.book;

public record BookSearchParametersDto(
        String title,
        String[] author,
        String isbn,
        String priceFrom,
        String priceTo
) {
}
