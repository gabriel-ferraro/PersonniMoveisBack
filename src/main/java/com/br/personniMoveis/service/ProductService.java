package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.ProductDto;
import com.br.personniMoveis.dto.ProductGetDto;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.mapper.ProductMapper;
import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductGetDto findProductByIdOrThrowBadRequestException(Long id, String exceptionMessage) {
        return ProductMapper.INSTANCE.ProductToProductGetDto(
                productRepository.findById(id).orElseThrow(
                        () -> new BadRequestException(exceptionMessage)));
    }

//    public Page<ProductDto> getAllProducts(Pageable pageable) {
//        return productRepository.findAllProducts(pageable);
//    }
//    public Page<ProductDto> getAllProducts(Pageable pageable) {
//        return productRepository.findAllProducts(pageable);
//    }
//    public Page<ProductDto> getAllProducts(Pageable pageable) {
//        return productRepository.findAllProducts(pageable);
//    }
    public void createProduct(ProductDto productDto) {
        // cria novo produto.
        Product newProduct = ProductMapper.INSTANCE.toProduct(productDto);
        // persiste no BD.
        productRepository.save(newProduct);
    }

    public void updateProduct(ProductDto productDto, Long productId) {
        // Encontra produto existente para atualiza-lo ou joga exceção.
        findProductByIdOrThrowBadRequestException(productId, "Product not found");
        // Faz alteracoes no produto.
        Product productToBeUpdated = ProductMapper.INSTANCE.toProduct(productDto);
        productToBeUpdated.setProductId(productId);
        // Persiste alteracoes.
        productRepository.save(productToBeUpdated);
    }

    public void deleteProductById(Long productId) {
        // Econtra produto ou joga exceção.
        findProductByIdOrThrowBadRequestException(productId, "Product not found");
        // Deleta produto via id.
        productRepository.deleteById(productId);
    }
}
