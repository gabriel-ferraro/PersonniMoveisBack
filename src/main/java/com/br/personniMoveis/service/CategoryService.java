package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.CategoryDto.CategoryPostDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryPutDto;
import com.br.personniMoveis.dto.product.get.ProductGetDto;
import com.br.personniMoveis.dto.product.post.CategoryDto;
import com.br.personniMoveis.dto.product.post.ProductDto;
import com.br.personniMoveis.dto.product.post.SectionDto;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.mapper.Category.CategoryMapper;
import com.br.personniMoveis.model.category.Category;
import com.br.personniMoveis.model.product.*;
import com.br.personniMoveis.repository.CategoryRepository;
import com.br.personniMoveis.service.product.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<com.br.personniMoveis.dto.product.get.CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(
                CategoryMapper.INSTANCE::categoryToCategoryList).toList();
    }

    public List<ProductGetDto> getAllProductsInCategory(Long categoryId) {
        return categoryRepository.getAllProductsInCategory(categoryId);
    }

    /**
     * Recebe requisição do endpoint e decide se vai  realizar edição ou salvar itens da categoria.
     *
     * @param categoryId          Id da categoria a ser salva.
     * @param categoryDto dto com itens a serem editados na categoria.
     */
    public Category createOrUpdateRegularProduct(Long categoryId, CategoryDto categoryDto) {
        Category category = null;
        // Se id não é nulo, a categoria existente é adquirida do BD para fazer a edição.
        if (categoryId != null) {
            // Checa se a categoria existe no BD pelo id e cria instância da categoria existente.
            category = findCategoryOrThrowNotFoundException(categoryId);
        } else {
            // Cria nova instância para fazer a criação.
            category = CategoryMapper.INSTANCE.categoryProductPostDtoToCategory(categoryDto);
        }
        // Chama o método para salvar ou editar os itens do dto na categoria do BD.
        this.saveRegularProduct(category, categoryDto);
        // retorna a criação/alteração realizada.
        return categoryRepository.save(category);
    }

    /**
     * Faz operações para associar o produto regular criado/atualizado com uma única requisição ao endpoint.
     *
     * @param cpp Dto com info para criação do produto regular inteiro. Dados nulos são validados.
     */
    @Transactional
    public void saveRegularProduct(Category category, CategoryDto cpp) {
        List<Product> newProdList = new ArrayList<>();
        // Faz a associação dos dados para cada produto da categoria.
        cpp.getProductList().forEach(p -> {
            Product newProduct = productService.createProduct(p);
            newProdList.add(newProduct);
            // Adquire o payload válido dos valores vindos do JSON e salva/edita no BD.
            newProduct.getDetails().addAll(this.saveDetailsInProduct(p));
            newProduct.getMaterials().addAll(this.saveMaterialsInProduct(p));
            newProduct.getTags().addAll(this.saveTagsInProduct(p));
            newProduct.getSections().addAll(this.saveSectionsInProduct(p));
        });
        // Salva dados dos produtos da categoria no BD.
        category.getProducts().addAll(newProdList);
    }

    private List<Detail> saveDetailsInProduct(ProductDto p) {
        List<Detail> newDetailList = new ArrayList<>();
        if (p.getDetailPostList() != null) {
            p.getDetailPostList().forEach(d -> {
                Detail newDetail = productService.createDetail(d);
                newDetailList.add(newDetail);
            });
        }
        return newDetailList;
    }

    private List<Material> saveMaterialsInProduct(ProductDto p) {
        List<Material> newMaterialList = new ArrayList<>();
        if (p.getMaterialPostList() != null) {
            p.getMaterialPostList().forEach(m -> {
                Material newMaterial = productService.createMaterial(m);
                newMaterialList.add(newMaterial);
            });
        }
        return newMaterialList;
    }

    private List<Tag> saveTagsInProduct(ProductDto p) {
        List<Tag> newTagList = new ArrayList<>();
        if (p.getTagPostList() != null) {
            p.getTagPostList().forEach(t -> {
                Tag newTag = productService.createTag(t);
                newTagList.add(newTag);
            });
        }
        return newTagList;
    }

    private List<Section> saveSectionsInProduct(ProductDto p) {
        List<Section> newSections = new ArrayList<>();
        if (p.getSectionPostList() != null) {
            p.getSectionPostList().forEach(s -> {
                Section newSection = productService.createSection(s);
                List<Option> newOptions = this.saveOptionsInSection(s);
                newSection.getOptions().addAll(newOptions);
                newSections.add(newSection);
            });
        }
        return newSections;
    }

    private List<Option> saveOptionsInSection(SectionDto s) {
        List<Option> newOptions = new ArrayList<>();
        if (!s.getOptionPostDtoList().isEmpty()) {
            s.getOptionPostDtoList().forEach(o -> {
                Option newOption = productService.createOption(o);
                newOptions.add(newOption);
            });
        }
        return newOptions;
    }

    public void createCategoryCmp(CategoryPostDto categoryPostDto) {
        // cria nova categoria.
        Category newCategory = CategoryMapper.INSTANCE.categoryPostDtoToCategory(categoryPostDto);
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
        Category CategoryBeUpdated = CategoryMapper.INSTANCE.categoryPutDtoToCategory(categoryPutDto);

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
