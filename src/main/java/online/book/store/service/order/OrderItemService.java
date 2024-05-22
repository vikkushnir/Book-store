package online.book.store.service.order;

import java.util.List;
import online.book.store.dto.response.order.OrderItemResponseDto;
import org.springframework.data.domain.Pageable;

public interface OrderItemService {
    List<OrderItemResponseDto> getAllOrderItemsForSpecificOrder(
            Pageable pageable,
            Long orderId
    );

    OrderItemResponseDto getSpecificItemFromOrder(
            Long userId,
            Long orderId,
            Long itemId
    );
}
