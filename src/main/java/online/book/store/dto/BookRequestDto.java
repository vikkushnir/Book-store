package online.book.store.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class BookRequestDto {
    @NotBlank(message = "title field shouldn't be empty")
    private String title;
    @NotBlank(message = "author field shouldn't be empty")
    private String author;
    @NotBlank
    @Size(min = 13, message = "ISBN should be at least 13 digits")
    private String isbn;
    @NotNull(message = "price field can't be empty")
    @Min(value = 0, message = "price can't be negative")
    private BigDecimal price;
    private String description;
    private String coverImage;
}
