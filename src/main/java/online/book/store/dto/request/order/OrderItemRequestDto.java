package online.book.store.dto.request.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderItemRequestDto {
    @NotBlank
    private String shippingAddress;
}
