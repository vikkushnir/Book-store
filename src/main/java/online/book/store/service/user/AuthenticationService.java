package online.book.store.service.user;

import online.book.store.dto.request.user.UserLoginRequestDto;
import online.book.store.dto.response.user.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto requestDto);
}
