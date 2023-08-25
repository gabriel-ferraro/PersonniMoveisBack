package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.CategoryDto.CategoryGetDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryPostDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryPutDto;
import com.br.personniMoveis.dto.product.post.CategoryProductPost;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.mapper.Category.CategoryMapper;
import com.br.personniMoveis.mapper.product.ProductMapper;
import com.br.personniMoveis.model.category.Category;
import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.repository.CategoryRepository;
import com.br.personniMoveis.service.product.ProductService;
import com.br.personniMoveis.utils.ValidationUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final ProductService productService;

    private final SectionCmpService sectionCmpService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductService productService, SectionCmpService sectionCmpService) {
        this.categoryRepository = categoryRepository;
        this.sectionCmpService = sectionCmpService;
        this.productService = productService;
    }

    public Category findCategoryOrThrowNotFoundException(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new BadRequestException("categoria não encontrada"));
    }

    public List<CategoryGetDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(
                CategoryMapper.INSTANCE::categoryToCategoryGetDto).toList();
    }

    /**
     * Retorna o produto regular criado com uma única requisição ao endpoint.
     *
     * @param cpp Dto com info para criação do produto regular inteiro.
     * @return o produto regular persistido após uma única requisição ao endpoint.
     */
    @Transactional
    public CategoryGetDto createRegularProduct(CategoryProductPost cpp) {
        // Traduzindo dto e persistindo categoria.
        Category newCategory = categoryRepository.save(CategoryMapper.INSTANCE.categoryProductPostDtoToCategory(cpp));
        // Adquirindo produtos.
        Set<Product> newProductList = cpp.getProductList().stream().map(ProductMapper.INSTANCE::toProduct)
                .collect(Collectors.toSet());
        // Persistindo produtos
        newProductList = newProductList.stream().map(productService::createProduct).collect(Collectors.toSet());
        // Associando produtos com a categoria.
        newCategory.setProducts(newProductList);
//        newProductList.forEach(p -> p.setCategory(newCategory));

        // Retornando criação completa.
        return CategoryMapper.INSTANCE.categoryToCategoryGetDto(newCategory);
    }

    public void createCategoryCmp(CategoryPostDto categoryPostDto) {
        // cria nova categoria.
        Category newCategory = CategoryMapper.INSTANCE.toCategory(categoryPostDto);
        // persiste no BD.
        categoryRepository.save(newCategory);

        //Vê se tem alguma seção cadastrada junto com a categoria
        categoryPostDto.getSectionCmpPostDtos().forEach(item -> {
            if (item.getName() != "") {
                sectionCmpService.createSectionCmp(categoryPostDto.getSectionCmpPostDtos(), newCategory.getCategoryId());
            }
        });


    }

    public void updateCategory(CategoryPutDto categoryPutDto, Long categoryId) {
        // Encontra produto existente para atualiza-lo ou joga exceção.
        findCategoryOrThrowNotFoundException(categoryId);

        // Faz alteracoes no produto.
        Category CategoryBeUpdated = CategoryMapper.INSTANCE.toCategoryPut(categoryPutDto);

        CategoryBeUpdated.setCategoryId(categoryId);
        // Persiste alteracoes.
        categoryRepository.save(CategoryBeUpdated);

        //Vê se tem alguma seção cadastrada junto com a categoria
        categoryPutDto.getSectionCmpPutDtos().forEach(item -> {
            if (item.getName() != "" && item.getSectionCmpId() != null || item.getSectionCmpId() != 0) {
                sectionCmpService.updateSectionCmp(categoryPutDto.getSectionCmpPutDtos(), item.getSectionCmpId());
            }
        });
    }

    public void deleteCategoryById(Long categoryId) {
        // Econtra produto ou joga exceção.
        findCategoryOrThrowNotFoundException(categoryId);
        // Deleta produto via id.
        categoryRepository.deleteById(categoryId);
    }
}
