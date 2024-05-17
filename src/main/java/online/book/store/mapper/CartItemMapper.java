package online.book.store.mapper;

import online.book.store.config.MapperConfig;
import online.book.store.dto.request.cartitem.CartItemRequestDto;
import online.book.store.dto.response.cartitem.CartItemResponseDto;
import online.book.store.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {

    CartItem toEntity(CartItemRequestDto requestDto);

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemResponseDto toDto(CartItem cartItem);
}
