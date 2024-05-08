package online.book.store.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import online.book.store.validation.FieldMatch;

@Getter
@Setter
@FieldMatch(field = "password",
        fieldMatch = "repeatPassword",
        message = "The password fields must match")
public class UserRegistrationRequestDto {
    @NotBlank(message = "field can't be empty")
    private String email;
    @NotBlank(message = "field can't be empty")
    @Size(min = 8, message = "field should contain at least 8 character")
    private String password;
    @NotBlank(message = "please repeat your password!")
    private String repeatPassword;
    @NotBlank(message = "field can't be empty")
    private String firstName;
    @NotBlank(message = "field can't be empty")
    private String lastName;
    private String shippingAddress;
}
