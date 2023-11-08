package com.br.personniMoveis.service.product;

import com.br.personniMoveis.dto.product.DetailDto;
import com.br.personniMoveis.dto.product.ProductDto;
import com.br.personniMoveis.dto.product.ProductPutDto;
import com.br.personniMoveis.dto.product.get.ProductGetDto;
import com.br.personniMoveis.exception.AlreadyExistsException;
import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.mapper.product.DetailMapper;
import com.br.personniMoveis.mapper.product.ProductMapper;
import com.br.personniMoveis.model.product.*;
import com.br.personniMoveis.repository.ProductRepository;
import com.br.personniMoveis.service.CategoryService;
import com.br.personniMoveis.service.EmailService;
import com.br.personniMoveis.utils.AuthUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImgService productImgService;
    private final CategoryService categoryService;
    private final DetailService detailService;
    private final SectionService sectionService;
    private final OptionService optionService;
    private final TagService tagService;
    private final AuthUtils authUtils;
    private final EmailService emailService;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductImgService productImgService,
                          CategoryService categoryService, DetailService detailService, SectionService sectionService,
                          OptionService optionService, TagService tagService, AuthUtils authUtils, EmailService emailService) {
        this.productRepository = productRepository;
        this.productImgService = productImgService;
        this.categoryService = categoryService;
        this.detailService = detailService;
        this.sectionService = sectionService;
        this.optionService = optionService;
        this.tagService = tagService;
        this.authUtils = authUtils;
        this.emailService = emailService;
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
     * Retorna todos produtos vigentes.
     *
     * @return Lista de todos produtos.
     */
    @Transactional
    public List<Product> getAllProducts() {
        return productRepository.findByIsRemovedFalse();
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

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product createProduct(ProductDto productDto) {
        return productRepository.save(ProductMapper.INSTANCE.productDtoToProduct(productDto));
    }

    /**
     * Cria produto convencional completo (recebe payload para criação do produto e todos subitens).
     *
     * @param product    requisição para criação do produto.
     * @param categoryId id opcional da categoria do produto (produto pode não estar em uma categoria).
     * @return O produto persistido no banco.
     */
    @Transactional
    public Product createFullProduct(Product product, Long categoryId) {
        Product newProd = new Product();
        newProd.setName(product.getName());
        newProd.setValue(product.getValue());
        newProd.setQuantity(product.getQuantity());
        newProd.setEditable(product.getEditable());
        newProd.setMainImg(product.getMainImg());
        newProd.setDescription(product.getDescription());
        // Seta disponibilidade de produto de acordo com a quantidade em estoque.
        newProd.setAvailable(product.getAvailable() && product.getQuantity() > 0);
        newProd.setIsRemoved(false);
        // Faz set da categoria caso tenha sido informada.
        if (categoryId != null) {
            // Seta id da categoria para possuir sua referência no produto.
            newProd.setCategory(categoryService.findCategoryOrThrowNotFoundException(categoryId));
            newProd.setCategoryId(categoryId);
        }
        if (product.getDetails() != null && !product.getDetails().isEmpty()) {
            newProd.setDetails(product.getDetails());
        }
        // seta imagens secundarias.
        if (product.getSecondaryImages() != null && !product.getSecondaryImages().isEmpty()) {
            newProd.setSecondaryImages(product.getSecondaryImages());
        }
        // Set de seções.
        if (product.getSections() != null && !product.getSections().isEmpty()) {
            Set<Section> newSections = new HashSet<>();
            for (Section section : product.getSections()) {
                Set<Option> newOptions = new HashSet<>();
                if (section.getOptions() != null && !section.getOptions().isEmpty()) {
                    for (Option option : section.getOptions()) {
                        optionService.saveOption(option);
                        newOptions.add(option);
                    }
                }
                // Atualize a lista de opções da seção com as novas opções.
                section.setOptions(newOptions);
                sectionService.saveSection(section);
                // lista novas seções.
                newSections.add(section);
            }
            newProd.setSections(newSections);
        }
        // Seta data de criação do prod para agora.
        newProd.setDtCreated(LocalDateTime.now());
        // Persiste produto.
        return productRepository.save(newProd);
    }

    @Transactional
    public Product updateFullProduct(Product product, Long categoryId) {
        this.findProductOrThrowNotFoundException(product.getProductId());
        // seta catId.
        if (categoryId != null) {
            product.setCategoryId(categoryId);
        }
        // Determina que esta vigente.
        product.setIsRemoved(false);
        // seta data de update;
        product.setDtUpdated(LocalDateTime.now());
        return productRepository.save(product);
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
    public void removeDetailInProduct(String token, Long productId, Long detailId) {
        authUtils.validateUserAdmin(token);
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

    @Transactional
    public void updateProduct(ProductPutDto productDto, Long productId) {
        // Encontra produto existente para atualizá-lo ou lança exceção.
        Product existingProduct = this.findProductOrThrowNotFoundException(productId);
        // Faz alterações no produto.
        Product productToBeUpdated = ProductMapper.INSTANCE.productPutDtoToProduct(productDto);
        // id.
        productToBeUpdated.setProductId(productId);
        // Vigente.
        productToBeUpdated.setIsRemoved(false);
        // Seta data de atualização.
        productToBeUpdated.setDtUpdated(LocalDateTime.now());
        // details.
        if (productDto.getDetails() != null && !productDto.getDetails().isEmpty()) {
            productDto.getDetails().forEach(detailService::saveDetail);
        }
        // imagens secundarias.
        if (productDto.getSecondaryImages() != null && !productDto.getSecondaryImages().isEmpty()) {
            productDto.getSecondaryImages().forEach(productImgService::saveProductImg);
        }
        // Set para salvar as seções.
        Set<Section> updatedSections = new HashSet<>();
        if (productDto.getSections() != null && !productDto.getSections().isEmpty()) {
            for (Section section : productDto.getSections()) {
                Set<Option> updatedOptions = new HashSet<>();
                if (section.getOptions() != null && !section.getOptions().isEmpty()) {
                    for (Option option : section.getOptions()) {
                        optionService.saveOption(option);
                        updatedOptions.add(option);
                    }
                }
                section.setOptions(updatedOptions);
                sectionService.saveSection(section);
                updatedSections.add(section);
            }
        }
        // salvando seções.
        productToBeUpdated.setSections(updatedSections);
        // Persiste alteracoes.
        productRepository.save(productToBeUpdated);
    }

    /**
     * Delete lógico do produto no BD.
     */
    @Transactional
    public void deleteProduct(Long productId) {
        Product prod = findProductOrThrowNotFoundException(productId);
        prod.setIsRemoved(true);
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

    public void notifyClientsProductReturned(Long productId, String productUrl) {
        Product product = this.findProductOrThrowNotFoundException(productId);
        // Identifica usuários que estão na lista de espera pelo produto e envia e-mail.
        product.getUsers().forEach(user -> emailService.productArrivedMessage(user.getEmail(), user.getName(), product, productUrl));
    }
}
