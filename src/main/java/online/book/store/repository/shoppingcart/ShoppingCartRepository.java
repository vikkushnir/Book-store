package online.book.store.repository.shoppingcart;

import java.util.Optional;
import online.book.store.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("FROM ShoppingCart sc JOIN FETCH sc.cartItems")
    ShoppingCart findByUserEmail(String email);

    Optional<ShoppingCart> findByUserId(Long userId);
}
