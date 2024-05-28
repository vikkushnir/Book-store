package online.book.store.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import online.book.store.dto.request.category.CategoryRequestDto;
import online.book.store.dto.response.category.CategoryResponseDto;
import online.book.store.model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {
    private static final Long CATEGORY_ID = 1L;
    private static final String CATEGORY_NAME = "Comic Science Fiction";
    private static final String CATEGORY_DESCRIPTION = "Books that blend "
            + "science fiction with humor.";
    private static final int EXPECTED_ALL_CATEGORIES = 8;
    private static final int CATEGORY_ID_ONE = 1;
    private static final String CATEGORIES_ID_URL = "/categories/{id}";
    private static final String CATEGORIES_URL = "/categories";

    private static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Create category")
    public void createCategory_ValidRequest_Success() throws Exception {
        CategoryRequestDto categoryRequestDto = defaultCategoryRequestDto();

        Category category = toModel(categoryRequestDto);
        category.setId(CATEGORY_ID);

        CategoryResponseDto expected = toDto(category);

        String jsonRequest = objectMapper.writeValueAsString(categoryRequestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(CATEGORIES_URL)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryResponseDto.class);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Get all categories")
    public void getAll_GetAllCategories_ReturnListCategory() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(CATEGORIES_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<CategoryResponseDto> actual = List.of(
                objectMapper.readValue(result.getResponse().getContentAsString(),
                        CategoryResponseDto[].class));

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(EXPECTED_ALL_CATEGORIES, actual.size());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Return category by id")
    public void getCategoryById_ValidId_ReturnCategoryDto() throws Exception {
        CategoryResponseDto expected = new CategoryResponseDto();
        expected.setId(CATEGORY_ID);
        expected.setName(CATEGORY_NAME);
        expected.setDescription(CATEGORY_DESCRIPTION);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(CATEGORIES_ID_URL, CATEGORY_ID_ONE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryResponseDto.class);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(CATEGORY_ID_ONE, actual.getId());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete category")
    public void deleteCategory_ValidRequest_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(CATEGORIES_ID_URL, CATEGORY_ID_ONE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private CategoryRequestDto defaultCategoryRequestDto() {
        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName(CATEGORY_NAME);
        requestDto.setDescription(CATEGORY_DESCRIPTION);
        return requestDto;
    }

    private CategoryResponseDto toDto(Category category) {
        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setId(category.getId());
        responseDto.setName(category.getName());
        responseDto.setDescription(category.getDescription());
        return responseDto;
    }

    private Category toModel(CategoryRequestDto requestDto) {
        Category category = new Category();
        category.setName(requestDto.getName());
        category.setDescription(requestDto.getDescription());
        return category;
    }
}
