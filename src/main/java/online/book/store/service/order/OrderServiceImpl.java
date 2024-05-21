package online.book.store.service.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import online.book.store.dto.request.order.OrderItemRequestDto;
import online.book.store.dto.request.order.UpdateRequestOrderItemDto;
import online.book.store.dto.response.order.OrderResponseDto;
import online.book.store.exception.EntityNotFoundException;
import online.book.store.mapper.OrderMapper;
import online.book.store.model.Order;
import online.book.store.model.OrderItem;
import online.book.store.model.ShoppingCart;
import online.book.store.model.User;
import online.book.store.repository.cartitem.CartItemRepository;
import online.book.store.repository.order.OrderItemRepository;
import online.book.store.repository.order.OrderRepository;
import online.book.store.repository.shoppingcart.ShoppingCartRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderResponseDto> getOrdersHistory(Long userId, Pageable pageable) {
        List<Order> ordersHistory = orderRepository.findAllByUserId(userId, pageable);
        return ordersHistory.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderResponseDto placeOrder(User user, OrderItemRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't get shopping cart with user's id: "
                        + user.getId())
        );
        Order order = createOrder(user, requestDto);
        Set<OrderItem> orderItems = getOrderItems(shoppingCart, order);
        BigDecimal totalPrice = getSumOfAllItems(orderItems);

        order.setTotal(totalPrice);
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        cartItemRepository.deleteAll(shoppingCart.getCartItems());

        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrderStatus(Long id, UpdateRequestOrderItemDto requestDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't get order with id: " + id)
        );
        order.setStatus(requestDto.getStatus());

        return orderMapper.toDto(orderRepository.save(order));
    }

    private static Order createOrder(User user, OrderItemRequestDto requestDto) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.getShippingAddress());
        return order;
    }

    private static Set<OrderItem> getOrderItems(ShoppingCart shoppingCart, Order order) {
        return shoppingCart.getCartItems()
                .stream()
                .map(item -> new OrderItem(
                        order,
                        item.getBook(),
                        item.getQuantity(),
                        item.getBook().getPrice()))
                .collect(Collectors.toSet());
    }

    private static BigDecimal getSumOfAllItems(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
