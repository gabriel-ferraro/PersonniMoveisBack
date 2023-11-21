package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.ProductImg;
import com.br.personniMoveis.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImg, Long>, JpaSpecificationExecutor<ProductImg> {

}
