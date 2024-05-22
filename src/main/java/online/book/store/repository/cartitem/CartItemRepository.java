package online.book.store.repository.cartitem;

import java.util.Optional;
import online.book.store.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("FROM CartItem ci JOIN FETCH ci.shoppingCart sc "
            + "WHERE ci.id = :itemId AND sc.user.id = :userId")
    Optional<CartItem> findByUserIdAndItemId(
            @Param("userId") Long userId,
            @Param("itemId") Long itemId);
}
