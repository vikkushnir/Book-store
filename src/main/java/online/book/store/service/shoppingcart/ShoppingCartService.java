package online.book.store.service.shoppingcart;

import online.book.store.dto.request.cartitem.CartItemRequestDto;
import online.book.store.dto.request.cartitem.UpdateRequestCartItemDto;
import online.book.store.dto.response.shoppingcart.ShoppingCartResponseDto;
import online.book.store.model.User;

public interface ShoppingCartService {
    void createShoppingCart(User user);

    ShoppingCartResponseDto getShoppingCart(String email);

    ShoppingCartResponseDto addItem(Long id, CartItemRequestDto requestDto);

    ShoppingCartResponseDto updateItem(Long userId, Long itemId,
                                       UpdateRequestCartItemDto requestDto);

    void deleteItem(Long id);
}
