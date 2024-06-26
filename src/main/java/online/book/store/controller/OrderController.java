package online.book.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import online.book.store.dto.request.order.OrderItemRequestDto;
import online.book.store.dto.request.order.UpdateRequestOrderItemDto;
import online.book.store.dto.response.order.OrderItemResponseDto;
import online.book.store.dto.response.order.OrderResponseDto;
import online.book.store.model.User;
import online.book.store.service.order.OrderItemService;
import online.book.store.service.order.OrderService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get orders history", description = "Get a list of all orders")
    public List<OrderResponseDto> getOrdersHistory(
            Authentication authentication,
            @ParameterObject @PageableDefault Pageable pageable
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrdersHistory(user.getId(), pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @Operation(summary = "Place an order", description = "Place an order")
    public OrderResponseDto placeOrder(
            Authentication authentication,
            @RequestBody @Valid OrderItemRequestDto requestDto
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.placeOrder(user, requestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    @Operation(summary = "Update an order status",
            description = "Updates status for specific order")
    public OrderResponseDto updateOrderStatus(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestOrderItemDto requestDto
    ) {
        return orderService.updateOrderStatus(id, requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}/items")
    @Operation(summary = "Get all item from a certain order",
            description = "Get all item from a certain order")
    public List<OrderItemResponseDto> getAllOrderItemsForSpecificOrder(
            @ParameterObject @PageableDefault Pageable pageable,
            @PathVariable Long orderId
    ) {
        return orderItemService.getAllOrderItemsForSpecificOrder(pageable, orderId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Get a specific item from a certain order",
            description = "Get a specific item from a certain order")
    public OrderItemResponseDto getSpecificItemFromOrder(
            Authentication authentication,
            @PathVariable Long orderId,
            @PathVariable Long itemId
    ) {
        User user = (User) authentication.getPrincipal();
        return orderItemService.getSpecificItemFromOrder(user.getId(), orderId, itemId);
    }
}
