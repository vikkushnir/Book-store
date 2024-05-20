package online.book.store.mapper;

import online.book.store.config.MapperConfig;
import online.book.store.dto.response.order.OrderResponseDto;
import online.book.store.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    OrderResponseDto toDto(Order order);
}
