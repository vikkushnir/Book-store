package online.book.store.service.category;

import java.util.List;
import online.book.store.dto.request.category.CategoryRequestDto;
import online.book.store.dto.response.category.CategoryResponseDto;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto findById(Long id);

    CategoryResponseDto save(CategoryRequestDto requestDto);

    CategoryResponseDto updateById(Long id, CategoryRequestDto requestDto);

    void deleteById(Long id);
}
