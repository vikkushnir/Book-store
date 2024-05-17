package online.book.store.service.shoppingcart;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.book.store.dto.request.cartitem.CartItemRequestDto;
import online.book.store.dto.request.cartitem.UpdateRequestCartItemDto;
import online.book.store.dto.response.shoppingcart.ShoppingCartResponseDto;
import online.book.store.exception.EntityNotFoundException;
import online.book.store.mapper.CartItemMapper;
import online.book.store.mapper.ShoppingCartMapper;
import online.book.store.model.Book;
import online.book.store.model.CartItem;
import online.book.store.model.ShoppingCart;
import online.book.store.model.User;
import online.book.store.repository.book.BookRepository;
import online.book.store.repository.cartitem.CartItemRepository;
import online.book.store.repository.shoppingcart.ShoppingCartRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto getShoppingCart(String email) {
        return shoppingCartMapper.toDto(shoppingCartRepository.findByUserEmail(email));
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto addItem(Long id, CartItemRequestDto requestDto) {
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Can't get book with id: " + requestDto.getBookId()));

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Can't get shopping cart with id: " + id));

        CartItem cartItem = cartItemMapper.toEntity(requestDto);
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCart);

        CartItem savedItem = cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(savedItem);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto updateItem(
            Long userId,
            Long itemId,
            UpdateRequestCartItemDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't get shopping cart for user id: " + userId));
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(
                itemId, shoppingCart.getId()).orElseThrow(() -> new EntityNotFoundException(
                        "Can't get item with id: " + itemId
                                + " in shopping cart with id: " + shoppingCart.getId()));
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public void deleteItem(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Can't get cart item with id: " + id));
        cartItemRepository.delete(cartItem);
    }
}
