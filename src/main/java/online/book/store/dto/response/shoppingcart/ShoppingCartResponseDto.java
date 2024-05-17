package online.book.store.dto.response.shoppingcart;

import java.util.List;
import online.book.store.dto.response.cartitem.CartItemResponseDto;

public record ShoppingCartResponseDto(
        Long id,
        Long userId,
        List<CartItemResponseDto> cartItems
) {
}
