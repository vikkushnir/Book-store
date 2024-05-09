package online.book.store.dto.request.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class BookRequestDto {
    @NotBlank(message = "field shouldn't be empty")
    private String title;
    @NotBlank(message = "field shouldn't be empty")
    private String author;
    @NotBlank
    @ISBN(message = "should be at least 13 digits")
    private String isbn;
    @NotNull(message = "field can't be empty")
    @Min(value = 0, message = "can't be negative")
    private BigDecimal price;
    private String description;
    private String coverImage;
}
