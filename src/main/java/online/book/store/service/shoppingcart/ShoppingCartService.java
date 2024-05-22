package online.book.store.service.shoppingcart;

import online.book.store.dto.request.cartitem.CartItemRequestDto;
import online.book.store.dto.request.cartitem.UpdateCartItemRequestDto;
import online.book.store.dto.response.shoppingcart.ShoppingCartResponseDto;
import online.book.store.model.User;

public interface ShoppingCartService {
    void createShoppingCart(User user);

    ShoppingCartResponseDto getShoppingCart(Long userId);

    ShoppingCartResponseDto addItem(Long id, CartItemRequestDto requestDto);

    ShoppingCartResponseDto updateItem(Long userId, Long itemId,
                                       UpdateCartItemRequestDto requestDto);

    void deleteItem(Long id);
}
