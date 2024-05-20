package online.book.store.dto.request.order;

import lombok.Data;
import online.book.store.model.Order;

@Data
public class UpdateRequestOrderItemDto {
    private Order.Status status;
}
