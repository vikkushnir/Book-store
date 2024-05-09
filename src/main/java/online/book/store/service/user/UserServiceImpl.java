package online.book.store.service.user;

import lombok.RequiredArgsConstructor;
import online.book.store.dto.request.user.UserRegistrationRequestDto;
import online.book.store.dto.response.user.UserResponseDto;
import online.book.store.exception.RegistrationException;
import online.book.store.mapper.UserMapper;
import online.book.store.model.User;
import online.book.store.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can't register user with this email: "
                    + requestDto.getEmail() + " is already exists");

        }
        User user = userMapper.toModel(requestDto);
        return userMapper.toDto(userRepository.save(user));
    }
}
