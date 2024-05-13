package online.book.store.mapper;

import online.book.store.config.MapperConfig;
import online.book.store.dto.request.user.UserRegistrationRequestDto;
import online.book.store.dto.response.user.UserResponseDto;
import online.book.store.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    User toModel(UserRegistrationRequestDto user);
}
