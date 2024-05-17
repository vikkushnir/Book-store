package online.book.store.dto.request.cartitem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemRequestDto {
    @NotNull(message = "bookId mustn't be null")
    private Long bookId;
    @NotNull
    @Positive(message = "quantity value must be more than 0")
    private int quantity;
}
