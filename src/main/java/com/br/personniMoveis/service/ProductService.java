package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.ProductDto;
import com.br.personniMoveis.dto.ProductGetDto;
import com.br.personniMoveis.exception.AlreadyExistsException;
import com.br.personniMoveis.exception.ResourceNotFoundException;
import com.br.personniMoveis.mapper.ProductMapper;
import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.model.product.Tag;
import com.br.personniMoveis.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private final TagService tagService;
    
    @Autowired
    public ProductService(ProductRepository productRepository, TagService tagService) {
        this.productRepository = productRepository;
        this.tagService = tagService;
    }
    
    public Product findProductByIdOrThrowNotFoundException(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    }
    
    public Page<ProductGetDto> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(
                ProductMapper.INSTANCE::productToProductGetDto);
    }
    
    public List<ProductGetDto> getAllProductsWithTagId(Long tagId) {
        findProductByIdOrThrowNotFoundException(tagId);
        return productRepository.findProductsByTagsTagId(tagId);
    }
    
    public void createProduct(ProductDto productDto) {
        Product newProduct = ProductMapper.INSTANCE.toProduct(productDto);
        // persiste no BD.
        productRepository.save(newProduct);
    }
    
    @Transactional
    public void assignTagToProduct(Long productId, Long tagId) {
        // Recupera tag e id do BD.
        Product product = findProductByIdOrThrowNotFoundException(productId);
        Tag tag = tagService.findTagByIdOrThrowNotFoundException(tagId);
        // Se produto já tem a tag.
        if (product.getTags().contains(tag)) {
            throw new AlreadyExistsException("Product already has this tag.");
        }
        // Faz associação entre tag e produto no BD.
        Product.addTag(product, tag);
    }
    
    @Transactional
    public void removeTagFromProduct(Long productId, Long tagId) {
        Product product = findProductByIdOrThrowNotFoundException(productId);
        Tag tag = tagService.findTagByIdOrThrowNotFoundException(tagId);
        // Remove associação.
        Product.removeTag(product, tag);
    }
    
    public void updateProduct(ProductDto productDto, Long productId) {
        // Encontra produto existente para atualiza-lo ou joga exceção.
        findProductByIdOrThrowNotFoundException(productId);
        // Faz alteracoes no produto.
        Product productToBeUpdated = ProductMapper.INSTANCE.toProduct(productDto);
        productToBeUpdated.setProductId(productId);
        // Persiste alteracoes.
        productRepository.save(productToBeUpdated);
    }
    
    public void deleteProductById(Long productId) {
        findProductByIdOrThrowNotFoundException(productId);
        // Deleta produto via id.
        productRepository.deleteById(productId);
    }
}
