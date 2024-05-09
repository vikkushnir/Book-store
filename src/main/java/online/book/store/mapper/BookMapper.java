package online.book.store.mapper;

import online.book.store.config.MapperConfig;
import online.book.store.dto.request.book.BookRequestDto;
import online.book.store.dto.response.book.BookResponseDto;
import online.book.store.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookResponseDto toDto(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Book toModel(BookRequestDto requestDto);
}
