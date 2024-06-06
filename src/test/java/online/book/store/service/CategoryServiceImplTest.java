package online.book.store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import online.book.store.dto.request.category.CategoryRequestDto;
import online.book.store.dto.response.category.CategoryResponseDto;
import online.book.store.mapper.CategoryMapper;
import online.book.store.model.Category;
import online.book.store.repository.category.CategoryRepository;
import online.book.store.service.category.CategoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    private static final String CATEGORY_NAME = "Fiction";
    private static final String CATEGORY_DESCRIPTION = "interesting book";
    private static final Long CATEGORY_ID = 1L;
    private static final int EXPECTED_CATEGORIES = 3;

    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Save a new category")
    void save_validRequestDto_success() {
        CategoryRequestDto requestDto = createCategoryRequestDto();
        Category category = createCategory();
        CategoryResponseDto expected = createCategoryResponseDto(requestDto);

        when(categoryMapper.toEntity(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(expected);

        CategoryResponseDto actual = categoryService.save(requestDto);

        assertEquals(expected, actual);
        assertNotNull(actual);
    }

    @Test
    @DisplayName("Find category by id")
    void findById_ValidCategoryId_ReturnValidCategoryDto() {
        Category category = createCategory();
        CategoryResponseDto responseDto = createCategoryResponseDto(category);
        String expected = responseDto.getName();

        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(responseDto);

        CategoryResponseDto result = categoryService.findById(CATEGORY_ID);
        String actual = result.getName();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find all categories")
    void findAll_GetAllCategories_ReturnAllCategories() {
        List<Category> categories = List.of(new Category(), new Category(), new Category());
        Page<Category> categoryPage = new PageImpl<>(categories);

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoryPage);
        when(categoryMapper.toDto(any())).thenReturn(new CategoryResponseDto());

        List<CategoryResponseDto> result = categoryService.findAll(Pageable.unpaged());
        int actual = result.size();

        assertEquals(EXPECTED_CATEGORIES, actual);
    }

    private CategoryRequestDto createCategoryRequestDto() {
        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName(CATEGORY_NAME);
        requestDto.setDescription(CATEGORY_DESCRIPTION);
        return requestDto;
    }

    private Category createCategory() {
        Category category = new Category();
        category.setId(CATEGORY_ID);
        category.setName(CATEGORY_NAME);
        category.setDescription(CATEGORY_DESCRIPTION);
        return category;
    }

    private CategoryResponseDto createCategoryResponseDto(CategoryRequestDto requestDto) {
        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setId(CATEGORY_ID);
        responseDto.setName(requestDto.getName());
        responseDto.setDescription(requestDto.getDescription());
        return responseDto;
    }

    private CategoryResponseDto createCategoryResponseDto(Category category) {
        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setId(category.getId());
        responseDto.setName(category.getName());
        responseDto.setDescription(category.getDescription());
        return responseDto;
    }
}
