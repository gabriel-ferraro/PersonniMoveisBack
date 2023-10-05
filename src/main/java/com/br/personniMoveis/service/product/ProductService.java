package com.br.personniMoveis.service.product;

import com.br.personniMoveis.dto.product.*;
import com.br.personniMoveis.dto.product.get.ProductGetDto;
import com.br.personniMoveis.exception.AlreadyExistsException;
import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.mapper.product.*;
import com.br.personniMoveis.model.product.*;
import com.br.personniMoveis.repository.ProductRepository;
import com.br.personniMoveis.service.CategoryService;
import com.br.personniMoveis.service.DetailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final DetailService detailService;
    private final MaterialService materialService;
    private final TagService tagService;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryService categoryService,
                          DetailService detailService, TagService tagService, MaterialService materialService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.detailService = detailService;
        this.tagService = tagService;
        this.materialService = materialService;
    }

    public Product findProductOrThrowNotFoundException(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Produto não encontrado."));
    }

    private Detail findDetailInProductOrThrowNotFoundException(Product product, Long detailId) {
        return product.getDetails().stream().filter(detail -> detail.getDetailId().equals(detailId)).findAny().orElseThrow(
                () -> new ResourceNotFoundException("Detalhe não encontrada no produto."));
    }

    private Tag findTagInProductOrThrowNotFoundException(Product product, Long tagId) {
        return product.getTags().stream().filter(tag -> tag.getTagId().equals(tagId)).findAny().orElseThrow(
                () -> new ResourceNotFoundException("Tag não encontrada no produto."));
    }

    /**
     * Retorna todos produtos.
     *
     * @return Lista de todos produtos.
     */
    @Transactional
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<ProductGetDto> getAllProductsWithTagId(Long tagId) {
        tagService.findTagOrThrowNotFoundException(tagId);
        return productRepository.findProductsInTag(tagId);
    }

    public List<Tag> getAllTagsFromProduct(Long productId) {
        this.findProductOrThrowNotFoundException(productId);
        return productRepository.findTagsFromProduct(productId);
    }

    @Transactional
    public List<Product> getMostRecentProducts(Integer amountOfProducts) {
        // Se parâmetro passado é nulo ou menor que 1, atribui padrão: 4.
        if (amountOfProducts == null || amountOfProducts < 1) {
            amountOfProducts = 4;
        }
        return productRepository.getMostRecentProducts(amountOfProducts);
    }

    public Product createProduct(ProductDto productDto) {
        return productRepository.save(ProductMapper.INSTANCE.productDtoToProduct(productDto));
    }

    /**
     * Cria produto convencional completo (recebe payload para criação do produto e todos subitens).
     *
     * @param product    requisição para criação do produto.
     * @param categoryId da opcional da categoria do produto (produto pode não estar em uma categoria).
     * @return O produto persistido no banco.
     */
    @Transactional
    public Product saveRegularProduct(Product product, Long categoryId) {
        // Checa se produto recebido tem materiais.
        if (product.getMaterials() != null && !product.getMaterials().isEmpty()) {
            product.getMaterials().forEach(materialService::saveMaterial);
        }
        // Checa se produto tem tags.
        if (product.getTags() != null && !product.getTags().isEmpty()) {
            product.getTags().forEach(tag -> {
                // Se tag tem id nulo, cria tag, senão inclui tag como nova tag do produto.
                if (tag.getTagId() == null) {
                    tagService.createTag(tag);
                }
            });
        }
        // Faz set da categoria caso tenha sido informada.
        if (categoryId != null) {
            product.setCategory(categoryService.findCategoryOrThrowNotFoundException(categoryId));
            // Seta id da categoria para possuir sua referência no produto.
            product.setCategoryId(categoryId);
        }
        // Seta data de criação. Se produto já tem data de criação, atualiza data de modificação.
        if (product.getDtCreated() == null) {
            product.setDtCreated(LocalDateTime.now());
        } else {
            product.setDtUpdated(LocalDateTime.now());
        }
        // Seta disponibilidade de produto de acordo com a quantidade em estoque.
        product.setAvailable(product.getQuantity() > 0);
        // Persiste produto.
        return productRepository.save(product);
    }

    public List<DetailDto> getAllDetailsFromProduct(Long productId) {
        Product product = findProductOrThrowNotFoundException(productId);
        return product.getDetails().stream().map(DetailMapper.INSTANCE::detailToDetailGetDto).toList();
    }

    /**
     * Persiste detalhe e insere no produto.
     *
     * @param productId id do produto.
     * @param detailDto Detail que se deseja criar e associar ao produto.
     */
    @Transactional
    public Detail assignDetailToProduct(Long productId, DetailDto detailDto) {
        Product product = findProductOrThrowNotFoundException(productId);
        // Adquire model de detail.
        Detail detail = DetailMapper.INSTANCE.detailDtoToDetail(detailDto);
        // Faz associação.
        detail.setProduct(product);
        product.getDetails().add(detail);
        // Retorna detail criado e associado.
        return detail;
    }

    public void updateDetail(Long productId, Long detailId, DetailDto detailDto) {
        Product product = findProductOrThrowNotFoundException(productId);
        findDetailInProductOrThrowNotFoundException(product, detailId);
        Detail newDetail = DetailMapper.INSTANCE.detailDtoToDetail(detailDto);
        newDetail.setDetailId(detailId);
        detailService.updateDetail(newDetail);
    }

    @Transactional
    public void removeDetailInProduct(Long productId, Long detailId) {
        Product product = findProductOrThrowNotFoundException(productId);
        Detail detail = findDetailInProductOrThrowNotFoundException(product, detailId);
        product.getDetails().remove(detail);
    }

    /**
     * Associa uma tag a um produto.
     *
     * @param productId Id do produto
     */
    @Transactional
    public void assignTagToProduct(Long productId, Long tagId) {
        // Recupera tag e product do BD.
        Product product = this.findProductOrThrowNotFoundException(productId);
        Tag tag = tagService.findTagOrThrowNotFoundException(tagId);
        // Se produto já tem a tag.
        if (product.getTags().contains(tag)) {
            throw new AlreadyExistsException("Produto já tem a tag.");
        }
        // Faz associação entre tag e produto no BD.
        product.getTags().add(tag);
        tag.getProducts().add(product);
    }

    public void updateProduct(ProductDto productDto, Long productId) {
        // Encontra produto existente para atualiza-lo ou joga exceção.
        this.findProductOrThrowNotFoundException(productId);
        // Faz alteracoes no produto.
        Product productToBeUpdated = ProductMapper.INSTANCE.productDtoToProduct(productDto);
        productToBeUpdated.setProductId(productId);
        // Persiste alteracoes.
        productRepository.save(productToBeUpdated);
    }

    @Transactional
    public void deleteProductById(Long productId) {
        // Remove todas as tags do produto.
        this.removeAllTagsInProduct(productId);
        productRepository.deleteById(productId);
    }

    @Transactional
    public void removeTagInProduct(Long productId, Long tagId) {
        // Encontra produto e tag.
        Product product = this.findProductOrThrowNotFoundException(productId);
        Tag tag = this.findTagInProductOrThrowNotFoundException(product, tagId);
        // Remove tag e atualiza produto.
        product.getTags().remove(tag);
    }

    @Transactional
    public void removeAllTagsInProduct(Long productId) {
        Product product = this.findProductOrThrowNotFoundException(productId);
        // Remove todas as tags do produto e salva alterações.
        product.getTags().clear();
    }
}
