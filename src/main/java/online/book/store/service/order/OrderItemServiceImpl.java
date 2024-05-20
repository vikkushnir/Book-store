package online.book.store.service.order;

import java.util.List;
import lombok.RequiredArgsConstructor;
import online.book.store.dto.response.order.OrderItemResponseDto;
import online.book.store.exception.EntityNotFoundException;
import online.book.store.mapper.OrderItemMapper;
import online.book.store.model.Order;
import online.book.store.model.OrderItem;
import online.book.store.repository.order.OrderItemRepository;
import online.book.store.repository.order.OrderRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemResponseDto> getAllOrderItemsForSpecificOrder(
            Pageable pageable, Long orderId
    ) {
        return orderItemRepository.findAllByOrderId(orderId, pageable)
                .stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto getSpecificItemFromOrder(Long userId, Long orderId, Long itemId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't get order with id: " + orderId));

        OrderItem orderItem = order.getOrderItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                                "Can't get order item with id: " + itemId)
                );

        return orderItemMapper.toDto(orderItem);
    }
}
