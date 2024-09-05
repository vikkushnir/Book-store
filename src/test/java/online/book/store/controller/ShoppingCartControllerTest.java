package online.book.store.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Set;
import online.book.store.dto.request.cartitem.CartItemRequestDto;
import online.book.store.dto.request.cartitem.UpdateCartItemRequestDto;
import online.book.store.dto.response.cartitem.CartItemResponseDto;
import online.book.store.dto.response.shoppingcart.ShoppingCartResponseDto;
import online.book.store.model.Role;
import online.book.store.model.User;
import online.book.store.service.shoppingcart.ShoppingCartService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShoppingCartControllerTest {
    private static final Long USER_ID = 1L;
    private static final Long ITEM_ID = 1L;
    private static final Long BOOK_ID = 1L;
    private static final Long SHOPPING_CART_ID = 1L;
    private static final int QUANTITY = 1;
    private static final int UPDATED_QUANTITY = 2;
    private static final String BOOK_TITLE = "The Hitchhiker's Guide to the Galaxy";
    private static final String CART_URL = "/cart";
    private static final String CART_ITEMS_URL = "/cart/cart-items/1";

    private static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private ShoppingCartService shoppingCartService;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    private void setUpSecurityContext() {
        Role userRole = new Role();
        userRole.setName(Role.RoleName.USER);
        userRole.setDeleted(false);

        User customUser = new User();
        customUser.setId(USER_ID);
        customUser.setEmail("sara.smith@example.com");
        customUser.setRoles(Set.of(userRole));

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        customUser, null, customUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Add item to shopping cart")
    @Sql(scripts = "classpath:database/update-create-shopping-cart.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void addItemToCart_AddItem_Success() throws Exception {
        setUpSecurityContext();

        ShoppingCartResponseDto responseDto = new ShoppingCartResponseDto(
                SHOPPING_CART_ID, USER_ID, List.of(
                        new CartItemResponseDto(ITEM_ID, BOOK_ID, BOOK_TITLE, QUANTITY))
        );
        CartItemRequestDto requestDto = new CartItemRequestDto();
        requestDto.setBookId(BOOK_ID);
        requestDto.setQuantity(QUANTITY);

        when(shoppingCartService.addItem(anyLong(), any(CartItemRequestDto.class)))
                .thenReturn(responseDto);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post(CART_URL)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("Get shopping cart")
    @Sql(scripts = "classpath:database/update-create-shopping-cart.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getShoppingCart_GetCart_Success() throws Exception {
        setUpSecurityContext();

        ShoppingCartResponseDto responseDto = new ShoppingCartResponseDto(
                SHOPPING_CART_ID, USER_ID, List.of(
                        new CartItemResponseDto(ITEM_ID, BOOK_ID, BOOK_TITLE, QUANTITY))
        );

        when(shoppingCartService.getShoppingCart(anyLong())).thenReturn(responseDto);

        mockMvc.perform(get(CART_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Update item in shopping cart")
    @Sql(scripts = "classpath:database/update-create-shopping-cart.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void updateItemInCart_UpdateItem_Success() throws Exception {
        setUpSecurityContext();

        ShoppingCartResponseDto responseDto = new ShoppingCartResponseDto(
                SHOPPING_CART_ID, USER_ID, List.of(
                        new CartItemResponseDto(ITEM_ID, BOOK_ID, BOOK_TITLE, UPDATED_QUANTITY))
        );
        UpdateCartItemRequestDto requestDto = new UpdateCartItemRequestDto();
        requestDto.setQuantity(UPDATED_QUANTITY);

        when(shoppingCartService.updateItem(
                anyLong(), anyLong(), any(UpdateCartItemRequestDto.class))).thenReturn(responseDto);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(put(CART_ITEMS_URL)
                .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Delete item from shopping cart")
    @Sql(scripts = "classpath:database/update-create-shopping-cart.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteItemFromCart_DeleteItem_Success() throws Exception {
        setUpSecurityContext();

        mockMvc.perform(delete(CART_ITEMS_URL))
                .andExpect(status().isNoContent());
    }
}
