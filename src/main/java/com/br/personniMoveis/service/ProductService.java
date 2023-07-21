package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.ProductPostDto;
import com.br.personniMoveis.dto.ProductPutDto;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.mapper.ProductMapper;
import com.br.personniMoveis.model.Product;
import com.br.personniMoveis.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findProductByIdOrThrowBadRequestException(Long id, String exceptionMessage) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(exceptionMessage));
    }
    
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> getHomeProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public void createProduct(ProductPostDto productDto) {
        // cria novo produto.
        Product newProduct = ProductMapper.INSTANCE.toProduct(productDto);
        // persiste no BD.
        productRepository.save(newProduct);
    }

    public void updateProduct(ProductPutDto productDto, Long productId) {
        // Encontra produto existente para atualiza-lo ou joga excecao.
        findProductByIdOrThrowBadRequestException(productId, "Product not found");
        // Faz alteracoes no produto.
        Product productToBeUpdated = ProductMapper.INSTANCE.toProduct(productDto);
        productToBeUpdated.setProductId(productId);
        // Persiste alteracoes.
        productRepository.save(productToBeUpdated);
    }

    public void deleteProductById(Long productId) {
        Product product = findProductByIdOrThrowBadRequestException(productId, "Product not found");
        productRepository.delete(product);
    }
}
