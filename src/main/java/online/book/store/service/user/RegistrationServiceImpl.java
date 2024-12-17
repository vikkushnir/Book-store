package online.book.store.service.user;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import online.book.store.dto.request.user.UserRegistrationRequestDto;
import online.book.store.dto.response.user.UserRegistrationResponseDto;
import online.book.store.exception.RegistrationException;
import online.book.store.mapper.UserMapper;
import online.book.store.model.Role.RoleName;
import online.book.store.model.User;
import online.book.store.repository.user.RoleRepository;
import online.book.store.repository.user.UserRepository;
import online.book.store.service.shoppingcart.ShoppingCartService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can't register user with this email: "
                    + requestDto.getEmail() + " is already exists");

        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRoles(Set.of(
                roleRepository.findByName(RoleName.USER)
                .orElseThrow(() ->
                        new RegistrationException("Can't register user with this role")))
        );
        shoppingCartService.createShoppingCart(user);
        return userMapper.toDto(userRepository.save(user));
    }
}
