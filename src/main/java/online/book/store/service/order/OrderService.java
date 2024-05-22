package online.book.store.service.order;

import java.util.List;
import online.book.store.dto.request.order.OrderItemRequestDto;
import online.book.store.dto.request.order.UpdateRequestOrderItemDto;
import online.book.store.dto.response.order.OrderResponseDto;
import online.book.store.model.User;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    List<OrderResponseDto> getOrdersHistory(Long userId, Pageable pageable);

    OrderResponseDto placeOrder(User user, OrderItemRequestDto requestDto);

    OrderResponseDto updateOrderStatus(
            Long id,
            UpdateRequestOrderItemDto requestDto
    );
}
