package online.book.store.mapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import online.book.store.config.MapperConfig;
import online.book.store.dto.request.book.BookRequestDto;
import online.book.store.dto.response.book.BookDtoWithoutCategoryIds;
import online.book.store.dto.response.book.BookResponseDto;
import online.book.store.model.Book;
import online.book.store.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookResponseDto toDto(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toModel(BookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategoryIds(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookResponseDto responseDto, Book book) {
        if (book.getCategories() != null) {
            Set<Long> categoryIds = book.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet());
            responseDto.setCategoryIds(categoryIds);
        } else {
            responseDto.setCategoryIds(Collections.emptySet());
        }
    }
}
