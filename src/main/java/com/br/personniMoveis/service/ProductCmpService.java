package com.br.personniMoveis.service;

import com.br.personniMoveis.dto.CategoryDto.CategoryGetByIdDto;
import com.br.personniMoveis.dto.ElementCmpDto.ElementProductCmpDto;
import com.br.personniMoveis.dto.OptionCmpDto.OptionProductCmpDto;
import com.br.personniMoveis.dto.ProductCmp.ProductCmpDto;
import com.br.personniMoveis.dto.ProductCmp.ProductCmpGetByIdDto;
import com.br.personniMoveis.dto.ProductCmp.ProductCmpGetDto;
import com.br.personniMoveis.dto.SectionCmpDto.SectionProductCmpDto;
import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.mapper.Category.CategoryMapper;
import com.br.personniMoveis.mapper.ProductCmp.ProductCmpMapper;
import com.br.personniMoveis.model.productCmp.ElementCmp;
import com.br.personniMoveis.model.productCmp.OptionCmp;
import com.br.personniMoveis.model.productCmp.ProductCmp;
import com.br.personniMoveis.model.productCmp.SectionCmp;
import com.br.personniMoveis.repository.ElementCmpRepository;
import com.br.personniMoveis.repository.OptionCmpRepository;
import com.br.personniMoveis.repository.ProductCmpRepository;
import com.br.personniMoveis.repository.SectionCmpRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class ProductCmpService {
    private final ProductCmpRepository productCmpRepository;

    private final SectionCmpRepository sectionCmpRepository;

    private final ElementCmpRepository elementCmpRepository;

    private final OptionCmpRepository optionCmpRepository;

    @Autowired
    public ProductCmpService(ProductCmpRepository productCmpRepository, SectionCmpRepository sectionCmpRepository, ElementCmpRepository elementCmpRepository, OptionCmpRepository optionCmpRepository) {
        this.productCmpRepository = productCmpRepository;
        this.sectionCmpRepository = sectionCmpRepository;
        this.elementCmpRepository = elementCmpRepository;
        this.optionCmpRepository = optionCmpRepository;
    }

    public ProductCmpGetDto findProductByIdOrThrowBadRequestException(Long id, String exceptionMessage) {
        return ProductCmpMapper.INSTANCE.ProductCmpToProductCmpGetDto(                productCmpRepository.findById(id).orElseThrow(
                        () -> new BadRequestException(exceptionMessage)));
    }

    public ProductCmpGetByIdDto findProdutctCmpByIdOrThrowBadRequestException(Long id){
        ProductCmp productCmp = productCmpRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductCmp not found"));

        ProductCmpGetByIdDto productCmpGetByIdDto = ProductCmpMapper.INSTANCE.ProductCmpToProductCmpGetByIdDto(productCmp);

        return productCmpGetByIdDto;
    }


    public void createProductCmp(ProductCmpDto productCmpCreateDto) {
        // Crie um novo produto
        ProductCmp newProductCmp = new ProductCmp();
        newProductCmp.setQuantity(productCmpCreateDto.getQuantity());
        newProductCmp.setImgUrl(productCmpCreateDto.getImgUrl());
        newProductCmp.setDescription(productCmpCreateDto.getDescription());

        // Mapeie a lista de seções
        for (SectionProductCmpDto sectionProductCmpDto : productCmpCreateDto.getSectionProductCmpDtos()) {
            SectionCmp sectionCmp = sectionCmpRepository.findById(sectionProductCmpDto.getSectionId())
                    .orElseThrow(() -> new EntityNotFoundException("SectionCmp not found"));

            // Adicione a seção ao produto
            newProductCmp.getSectionCmps().add(sectionCmp);

            // Mapeie os elementos para esta seção
            for (ElementProductCmpDto elementProductCmpDto : sectionProductCmpDto.getElementProductCmpDtos()) {
                ElementCmp elementCmp = elementCmpRepository.findById(elementProductCmpDto.getId())
                        .orElseThrow(() -> new EntityNotFoundException("ElementCmp not found"));

                // Adicione o elemento à seção
                sectionCmp.getElementCmps().add(elementCmp);

                // Mapeie a opção para este elemento usando o ID da opção
                Long optionId = elementProductCmpDto.getOptionProductCmpDto().getId();
                Set<OptionCmp> optionCmp = Collections.singleton(optionCmpRepository.findById(optionId)
                        .orElseThrow(() -> new EntityNotFoundException("OptionCmp not found")));

                // Associe a opção ao elemento
                elementCmp.setOptionCmps(optionCmp);
            }
        }
        // Calcule o valor total com base nas opções selecionadas
        double totalOptionPrice = calculateTotalOptionPrice(newProductCmp);
        //Multiplica o valor pela quantidade
        totalOptionPrice = totalOptionPrice * newProductCmp.getQuantity();
        // Atribua o valor total ao produto
        newProductCmp.setValueTotal(totalOptionPrice);
        // Salve o novo produto no banco de dados
        productCmpRepository.save(newProductCmp);
    }

    private double calculateTotalOptionPrice(ProductCmp productCmp) {
        double totalOptionPrice = 0.0;

        for (SectionCmp sectionCmp : productCmp.getSectionCmps()) {
            for (ElementCmp elementCmp : sectionCmp.getElementCmps()) {
                for (OptionCmp optionCmp : elementCmp.getOptionCmps()) {
                    totalOptionPrice += optionCmp.getPrice();
                }
            }
        }

        return totalOptionPrice;
    }


        public void updateProduct(ProductCmpDto productCmpDto, Long productId) {
        // Encontra produto existente para atualiza-lo ou joga exceção.
        findProductByIdOrThrowBadRequestException(productId, "Product not found");
        // Faz alteracoes no produto.
        ProductCmp productCmpoBeUpdated = ProductCmpMapper.INSTANCE.toProductCmp(productCmpDto);
        productCmpoBeUpdated.setId(productId);
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