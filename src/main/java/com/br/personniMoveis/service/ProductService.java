package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.ProductPostDto;
import com.br.personniMoveis.dto.ProductPutDto;
import com.br.personniMoveis.mapper.ProductMapper;
import com.br.personniMoveis.model.Product;
import com.br.personniMoveis.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductByIdOrThrowBadRequestException(Long id, String reasonMessage) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, reasonMessage));
    }

    public List<Product> getHomeProductList() {
        return productRepository.findAll();
    }

    public void createProduct(ProductPostDto productDto) {
        // cria novo produto.
        Product newProduct = ProductMapper.INSTANCE.toProduct(productDto);
        // persiste no BD.
        productRepository.save(newProduct);
    }

    public void updateProduct(ProductPutDto productDto) {
        // Encontra produto existente para atualiza-lo ou joga excecao.
        Product persistedProduct = findProductByIdOrThrowBadRequestException(
                productDto.getProductId(), "Product not found");
        // Faz alteracoes no produto.
        Product productToBeUpdated = ProductMapper.INSTANCE.toProduct(productDto);
        // Persiste alteracoes.
        productRepository.save(productToBeUpdated);
    }

    public void deleteProductById(Long id) {
        Product product = findProductByIdOrThrowBadRequestException(id, "Product not found");
        productRepository.delete(product);
    }
}
