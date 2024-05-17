package online.book.store.mapper;

import online.book.store.config.MapperConfig;
import online.book.store.dto.response.shoppingcart.ShoppingCartResponseDto;
import online.book.store.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "user.id")
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);
}
