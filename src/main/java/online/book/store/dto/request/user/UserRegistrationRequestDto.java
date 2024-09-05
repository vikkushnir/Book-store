package online.book.store.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import online.book.store.validation.FieldMatch;

@Data
@FieldMatch(field = "password",
        fieldMatch = "repeatPassword",
        message = "The password fields must match")
@Accessors(chain = true)
public class UserRegistrationRequestDto {
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 8)
    private String password;
    @NotBlank
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String shippingAddress;
}
