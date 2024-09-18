package online.book.store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import online.book.store.dto.request.cartitem.CartItemRequestDto;
import online.book.store.dto.request.cartitem.UpdateCartItemRequestDto;
import online.book.store.dto.response.cartitem.CartItemResponseDto;
import online.book.store.dto.response.shoppingcart.ShoppingCartResponseDto;
import online.book.store.exception.EntityNotFoundException;
import online.book.store.mapper.CartItemMapper;
import online.book.store.mapper.ShoppingCartMapper;
import online.book.store.model.Book;
import online.book.store.model.CartItem;
import online.book.store.model.ShoppingCart;
import online.book.store.repository.book.BookRepository;
import online.book.store.repository.cartitem.CartItemRepository;
import online.book.store.repository.shoppingcart.ShoppingCartRepository;
import online.book.store.service.shoppingcart.ShoppingCartServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceImplTest {
    private static final Long USER_ID = 1L;
    private static final Long BOOK_ID = 1L;
    private static final Long ITEM_ID = 1L;
    private static final Long SHOPPING_CART_ID = 1L;
    private static final int QUANTITY = 1;
    private static final int UPDATED_QUANTITY = 2;
    private static final String BOOK_TITLE = "The Hitchhiker's Guide to the Galaxy";

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @Mock
    private CartItemMapper cartItemMapper;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Test
    @DisplayName("Get shopping cart by valid user id")
    public void getShoppingCart_ValidUserId_ReturnShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        ShoppingCartResponseDto responseDto = new ShoppingCartResponseDto(
                SHOPPING_CART_ID, USER_ID, List.of(
                        new CartItemResponseDto(ITEM_ID, BOOK_ID, BOOK_TITLE, QUANTITY)));

        when(shoppingCartRepository.findByUserId(USER_ID)).thenReturn(Optional.of(shoppingCart));
        when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(responseDto);

        ShoppingCartResponseDto actual = shoppingCartService.getShoppingCart(USER_ID);

        assertEquals(responseDto, actual);
    }

    @Test
    @DisplayName("Get shopping cart by invalid user id")
    public void getShoppingCart_InvalidUserId_ThrowsException() {
        when(shoppingCartRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> shoppingCartService.getShoppingCart(USER_ID));
    }

    @Test
    @DisplayName("Add item to shopping cart")
    public void addItem_ValidRequest_AddsItemToCart() {
        Book book = new Book();

        CartItemRequestDto requestDto = new CartItemRequestDto();
        requestDto.setBookId(BOOK_ID);
        requestDto.setQuantity(QUANTITY);

        CartItem cartItem = new CartItem();
        ShoppingCart shoppingCart = new ShoppingCart();

        ShoppingCartResponseDto responseDto = new ShoppingCartResponseDto(
                SHOPPING_CART_ID, USER_ID, List.of(
                        new CartItemResponseDto(ITEM_ID, BOOK_ID, BOOK_TITLE, QUANTITY)));

        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));
        when(shoppingCartRepository.findByUserId(USER_ID)).thenReturn(Optional.of(shoppingCart));
        when(cartItemMapper.toEntity(requestDto)).thenReturn(cartItem);
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);
        when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(responseDto);

        ShoppingCartResponseDto actual = shoppingCartService.addItem(USER_ID, requestDto);

        assertEquals(responseDto, actual);
        verify(cartItemRepository).save(cartItem);
    }

    @Test
    @DisplayName("Update item in shopping cart")
    public void updateItem_ValidRequest_UpdatesItem() {
        CartItem cartItem = new CartItem();
        ShoppingCart shoppingCart = new ShoppingCart();
        cartItem.setShoppingCart(shoppingCart);
        UpdateCartItemRequestDto requestDto = new UpdateCartItemRequestDto();
        requestDto.setQuantity(UPDATED_QUANTITY);
        ShoppingCartResponseDto responseDto = new ShoppingCartResponseDto(
                SHOPPING_CART_ID, USER_ID, List.of(
                        new CartItemResponseDto(ITEM_ID, BOOK_ID, BOOK_TITLE, QUANTITY)));

        when(cartItemRepository.findByUserIdAndItemId(USER_ID, ITEM_ID))
                .thenReturn(Optional.of(cartItem));
        when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(responseDto);

        ShoppingCartResponseDto actual = shoppingCartService.updateItem(
                USER_ID, ITEM_ID, requestDto
        );

        assertEquals(responseDto, actual);
        verify(cartItemRepository).save(cartItem);
    }

    @Test
    @DisplayName("Delete item from shopping cart")
    public void deleteItem_ValidId_DeletesItem() {
        when(cartItemRepository.existsById(ITEM_ID)).thenReturn(true);

        shoppingCartService.deleteItem(ITEM_ID);

        verify(cartItemRepository).deleteById(ITEM_ID);
    }

    @Test
    @DisplayName("Delete item from shopping cart with invalid id")
    public void deleteItem_InvalidId_ThrowsException() {
        when(cartItemRepository.existsById(ITEM_ID)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> shoppingCartService.deleteItem(ITEM_ID));
    }
}
