package online.book.store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import online.book.store.dto.request.user.UserRegistrationRequestDto;
import online.book.store.dto.response.user.UserRegistrationResponseDto;
import online.book.store.exception.RegistrationException;
import online.book.store.mapper.UserMapper;
import online.book.store.model.Role;
import online.book.store.model.Role.RoleName;
import online.book.store.model.User;
import online.book.store.repository.user.RoleRepository;
import online.book.store.repository.user.UserRepository;
import online.book.store.service.shoppingcart.ShoppingCartService;
import online.book.store.service.user.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {
    private static final String EMAIL = "user@example.com";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encodedPassword";

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ShoppingCartService shoppingCartService;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    private UserRegistrationRequestDto registrationRequestDto;
    private User user;
    private UserRegistrationResponseDto registrationResponseDto;

    @BeforeEach
    void setUp() {
        registrationRequestDto = new UserRegistrationRequestDto();
        registrationRequestDto.setEmail(EMAIL);
        registrationRequestDto.setPassword(PASSWORD);

        user = new User();
        user.setEmail(registrationRequestDto.getEmail());
        user.setPassword(registrationRequestDto.getPassword());

        registrationResponseDto = new UserRegistrationResponseDto();
        registrationResponseDto.setEmail(user.getEmail());
    }

    @DisplayName("Register with new email returns UserRegistrationResponseDto")
    @Test
    void register_NewEmail_ReturnsUserRegistrationResponseDto() {
        when(userRepository.findByEmail(registrationRequestDto.getEmail()))
                .thenReturn(Optional.empty());
        when(userMapper.toModel(registrationRequestDto)).thenReturn(user);
        when(passwordEncoder.encode(registrationRequestDto.getPassword())).thenReturn(
                ENCODED_PASSWORD);
        when(roleRepository.findByName(RoleName.USER))
                .thenReturn(Optional.of(new Role()));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(registrationResponseDto);

        UserRegistrationResponseDto response = registrationService.register(registrationRequestDto);

        verify(userRepository).findByEmail(registrationRequestDto.getEmail());
        verify(userMapper).toModel(registrationRequestDto);
        verify(passwordEncoder).encode(registrationRequestDto.getPassword());
        verify(roleRepository).findByName(RoleName.USER);
        verify(shoppingCartService).createShoppingCart(user);
        verify(userRepository).save(user);
        verify(userMapper).toDto(user);

        String expected = registrationRequestDto.getEmail();
        String actual = response.getEmail();
        assertEquals(expected, actual);
    }

    @DisplayName("Register with existing email throws RegistrationException")
    @Test
    void register_ExistingEmail_ThrowsRegistrationException() {
        final String expectedEmail = registrationRequestDto.getEmail();
        when(userRepository.findByEmail(expectedEmail))
                .thenReturn(Optional.of(user));

        RegistrationException actual = assertThrows(RegistrationException.class, () ->
                registrationService.register(registrationRequestDto));

        String expectedMessage = String.format(
                "Can't register user with this email: %s is already exists", expectedEmail);
        assertEquals(expectedMessage, actual.getMessage());
    }
}
