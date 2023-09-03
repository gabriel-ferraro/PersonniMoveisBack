package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.CategoryDto.CategoryCmpDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetByIdDto;
import com.br.personniMoveis.dto.CategoryDto.CategoryGetDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionCmpDto;
import com.br.personniMoveis.dto.product.CategoryDto;
import com.br.personniMoveis.dto.product.ProductDto;
import com.br.personniMoveis.dto.product.SectionDto;
import com.br.personniMoveis.dto.product.get.ProductGetDto;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.mapper.Category.CategoryMapper;
import com.br.personniMoveis.model.category.Category;
import com.br.personniMoveis.model.product.*;
import com.br.personniMoveis.model.productCmp.ElementCmp;
import com.br.personniMoveis.model.productCmp.OptionCmp;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import com.br.personniMoveis.repository.CategoryRepository;
import com.br.personniMoveis.repository.ElementCmpRepository;
import com.br.personniMoveis.repository.OptionCmpRepository;
import com.br.personniMoveis.repository.SectionCmpRepository;
import com.br.personniMoveis.service.product.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductService productService;
    private final SectionCmpService sectionCmpService;
    private final SectionCmpRepository sectionCmpRepository;
    private final ElementCmpRepository elementCmpRepository;
    private final OptionCmpRepository optionCmpRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
                           SectionCmpService sectionCmpService,
                           SectionCmpRepository sectionCmpRepository,
                           ElementCmpRepository elementCmpRepository,
                           OptionCmpRepository optionCmpRepository,
                           ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.sectionCmpService = sectionCmpService;
        this.sectionCmpRepository = sectionCmpRepository;
        this.elementCmpRepository = elementCmpRepository;
        this.optionCmpRepository = optionCmpRepository;
        this.productService = productService;
    }

    public CategoryGetByIdDto findCategoryCmpByIdOrThrowBadRequestException(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Categoria não encontrada."));

        Set<SectionCmp> sectionCmps = sectionCmpRepository.findByCategoryId(id);
        Set<ElementCmp> elementCmps = new HashSet<>();

        for (SectionCmp sectionCmp : sectionCmps) {
            Set<ElementCmp> sectionElementCmps = elementCmpRepository.findBySectionCmpId(sectionCmp.getId());
            elementCmps.addAll(sectionElementCmps);

            for (ElementCmp elementCmp : sectionElementCmps) {
                Set<OptionCmp> optionCmps = optionCmpRepository.findByElementCmpId(elementCmp.getId());
                elementCmp.setOptionCmps(optionCmps);
            }

            sectionCmp.setElementCmps(sectionElementCmps);
        }

        CategoryGetByIdDto categoryGetByIdDto = CategoryMapper.INSTANCE.CategotyToCategoryGetByIdDto(category);
        categoryGetByIdDto.setSectionCmps(sectionCmps);
        return categoryGetByIdDto;
    }

    public void createCategoryCmp(CategoryCmpDto categoryCmpDto) {
        // Cria nova categoria.
        Category newCategory = CategoryMapper.INSTANCE.categoryCmpDtoToCategory(categoryCmpDto);
        // Persiste no BD.
        categoryRepository.save(newCategory);

        //Vê se tem alguma seção cadastrada junto com a categoria
        if(categoryCmpDto.getSectionCmpDtos() != null) {
            categoryCmpDto.getSectionCmpDtos().forEach(item -> {
                if (item.getName() != "") {
                    sectionCmpService.createSectionCmp(item, newCategory.getId());
                }
            });
        }
    }

    public void updateCategoryCmp(CategoryCmpDto categoryCmpDto, Long categoryId) {
        findCategoryOrThrowNotFoundException(categoryId);

        // Atualiza os dados da categoria
        Category updatedCategory = CategoryMapper.INSTANCE.categoryCmpDtoToCategory(categoryCmpDto);
        updatedCategory.setId(categoryId);
        categoryRepository.save(updatedCategory);

        // Atualiza seções existentes ou cria novas seções
        if(!categoryCmpDto.getSectionCmpDtos().isEmpty()) {
            for (SectionCmpDto sectionDto : categoryCmpDto.getSectionCmpDtos()) {
                if (sectionDto.getSectionId() != null && sectionDto.getSectionId() > 0) {
                    sectionCmpService.updateSectionCmp(sectionDto, sectionDto.getSectionId());
                } else {
                    sectionCmpService.createSectionCmp(sectionDto, categoryId);
                }
            }
        }
    }

    public void deleteCategoryById(Long id) {
        Category categoryToDelete = findCategoryOrThrowNotFoundException(id);

        // Verifica se há seções relacionadas à categoria
        Set<SectionCmp> sectionsWithCategory = sectionCmpRepository.findByCategoryId(id);
        if (!sectionsWithCategory.isEmpty()) {
            throw new BadRequestException("Não é possível deletar categoria, existem seções associadas.");
        }

        // Deleta a categoria
        categoryRepository.delete(categoryToDelete);
    }

    public Category findCategoryOrThrowNotFoundException(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new BadRequestException("categoria não encontrada"));
    }

    public List<CategoryGetDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryMapper.INSTANCE::CategotyToCategoryGetDto).toList();
    }

    public List<ProductGetDto> getAllProductsInCategory(Long categoryId) {
        findCategoryOrThrowNotFoundException(categoryId);
        return categoryRepository.getAllProductsInCategory(categoryId);
    }

    /**
     * Recebe requisição do endpoint e decide se vai  realizar edição ou salvar itens da categoria.
     *
     * @param categoryId  Id da categoria a ser salva.
     * @param categoryDto dto com itens a serem editados na categoria.
     */
    public Category createOrUpdateRegularProduct(Long categoryId, CategoryDto categoryDto) {
        Category category = null;
        // Se id não é nulo, adquire os dados do BD para fazer edição.
        if (categoryId != null) {
            // Checa se a categoria existe no BD pelo id e cria instância da categoria existente.
            category = findCategoryOrThrowNotFoundException(categoryId);
        } else {
            // Senão é feita a criação.
            category = CategoryMapper.INSTANCE.categoryDtoToCategory(categoryDto);
        }
        // Chama o método para salvar ou editar os itens do dto na categoria do BD.
        this.saveRegularProduct(category, categoryDto);
        // retorna a criação/alteração realizada.
        return categoryRepository.save(category);
    }

    /**
     * Faz operações para associar o produto regular criado/atualizado com uma única requisição ao endpoint.
     *
     * @param cDto Dto com info para criação do produto regular inteiro. Dados nulos são validados.
     */
    @Transactional
    public void saveRegularProduct(Category category, CategoryDto cDto) {
        List<Product> newProdList = new ArrayList<>();
        // Faz a associação dos dados para cada produto da categoria.
        cDto.getProductList().forEach(p -> {
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
}
