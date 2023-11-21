package com.br.personniMoveis.service.product;

import com.br.personniMoveis.model.ProductImg;
import com.br.personniMoveis.repository.ProductImgRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductImgService {

    private final ProductImgRepository productImgRepository;
    public ProductImgService(ProductImgRepository productImgRepository) {
        this.productImgRepository = productImgRepository;
    }

    public void saveProductImg(ProductImg prodImg) {
        productImgRepository.save(prodImg);
    }

    public void removeProductImg(Long id){
        productImgRepository.deleteById(id);
    }
}
