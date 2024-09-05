package online.book.store.dto.response.shoppingcart;

import java.util.List;
import lombok.experimental.Accessors;
import online.book.store.dto.response.cartitem.CartItemResponseDto;

@Accessors(chain = true)
public record ShoppingCartResponseDto(
        Long id,
        Long userId,
        List<CartItemResponseDto> cartItems
) {
}
