package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.ProductCmp.ProductCmpDtoCmp;
import com.br.personniMoveis.dto.ProductCmp.ProductCmpGetDto;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.mapper.ProductCmp.ProductCmpMapper;
import com.br.personniMoveis.model.productCmp.ProductCmp;
import com.br.personniMoveis.repository.ProductCmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCmpService {
    private final ProductCmpRepository productCmpRepository;

    @Autowired
    public ProductCmpService(ProductCmpRepository productCmpRepository){this.productCmpRepository = productCmpRepository;}

    public ProductCmpGetDto findProductByIdOrThrowBadRequestException(Long id, String exceptionMessage) {
        return ProductCmpMapper.INSTANCE.ProductCmpToProductCmpGetDto(
                productCmpRepository.findById(id).orElseThrow(
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
    public void createProductCmp(ProductCmpDtoCmp productCmpDtoCmp) {
        // cria novo produto.
        ProductCmp newProductCmp = ProductCmpMapper.INSTANCE.toProductCmp(productCmpDtoCmp);
        // persiste no BD.
        productCmpRepository.save(newProductCmp);
    }

    public void updateProduct(ProductCmpDtoCmp productCmpDtoCmp, Long productId) {
        // Encontra produto existente para atualiza-lo ou joga exceção.
        findProductByIdOrThrowBadRequestException(productId, "Product not found");
        // Faz alteracoes no produto.
        ProductCmp productCmpoBeUpdated = ProductCmpMapper.INSTANCE.toProductCmp(productCmpDtoCmp);
        productCmpoBeUpdated.setProductCmpId(productId);
        // Persiste alteracoes.
        productCmpRepository.save(productCmpoBeUpdated);
    }

    public void deleteProductById(Long productId) {
        // Econtra produto ou joga exceção.
        findProductByIdOrThrowBadRequestException(productId, "Product not found");
        // Deleta produto via id.
        productCmpRepository.deleteById(productId);
    }
}