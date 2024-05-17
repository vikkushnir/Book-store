package online.book.store.mapper;

import online.book.store.config.MapperConfig;
import online.book.store.dto.request.category.CategoryRequestDto;
import online.book.store.dto.response.category.CategoryResponseDto;
import online.book.store.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);

    Category toEntity(CategoryRequestDto categoryRequestDto);
}
