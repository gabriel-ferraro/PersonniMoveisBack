package com.br.personniMoveis.controller;

import com.br.personniMoveis.service.product.ProductImgService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("productImg")
public class ProductImgController {

    private final ProductImgService productImgService;

    public ProductImgController(ProductImgService productImgService) {
        this.productImgService = productImgService;
    }

    @DeleteMapping(path = "/{id}")
        public ResponseEntity<HttpStatus> deleteProductImg(@PathVariable("id") Long id) {
            productImgService.removeProductImg(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

}
