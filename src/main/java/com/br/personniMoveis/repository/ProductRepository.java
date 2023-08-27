package com.br.personniMoveis.repository;

import com.br.personniMoveis.dto.ProductGetDto;
import com.br.personniMoveis.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<ProductGetDto> findProductsByTagsTagId(Long tagId);
}
