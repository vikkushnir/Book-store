package online.book.store.service.user;

import online.book.store.dto.request.user.UserRegistrationRequestDto;
import online.book.store.dto.response.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);
}
