package online.book.store.repository.cartitem;

import java.util.Optional;
import online.book.store.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndShoppingCartId(Long itemId, Long cartId);
}
