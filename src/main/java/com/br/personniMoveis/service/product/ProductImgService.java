package com.br.personniMoveis.service.product;

import com.br.personniMoveis.model.product.ProductImg;
import com.br.personniMoveis.repository.ProductImgRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductImgService {

    private final ProductImgRepository productImgRepository;
    public ProductImgService(ProductImgRepository productImgRepository) {
        this.productImgRepository = productImgRepository;
    }

    public ProductImg saveProductImg(ProductImg prodImg) {
        return productImgRepository.save(prodImg);
    }

    public void removeProductImg(Long id){
        productImgRepository.deleteById(id);
    }
}
