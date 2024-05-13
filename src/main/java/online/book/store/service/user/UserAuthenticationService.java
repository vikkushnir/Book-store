package online.book.store.service.user;

import online.book.store.dto.request.user.UserLoginRequestDto;
import online.book.store.dto.request.user.UserRegistrationRequestDto;
import online.book.store.dto.response.user.UserLoginResponseDto;
import online.book.store.dto.response.user.UserResponseDto;

public interface UserAuthenticationService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);

    UserLoginResponseDto authenticate(UserLoginRequestDto requestDto);
}
