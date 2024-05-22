package online.book.store.dto.request.cartitem;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateCartItemRequestDto {
    @Min(value = 1, message = "quantity value must be more than 0")
    private int quantity;
}
