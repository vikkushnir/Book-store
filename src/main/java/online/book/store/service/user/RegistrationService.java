package online.book.store.service.user;

import online.book.store.dto.request.user.UserRegistrationRequestDto;
import online.book.store.dto.response.user.UserRegistrationResponseDto;

public interface RegistrationService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto);
}
