package com.br.personniMoveis.repository;

import com.br.personniMoveis.dto.product.get.ProductGetDto;
import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.model.product.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("SELECT new com.br.personniMoveis.dto.product.get.ProductGetDto(" +
            "p.productId, p.name, p.value, p.quantity, p.editable, p.imgUrl, p.description) " +
            "FROM Product p " +
            "JOIN p.tags t " +
            "WHERE t.tagId = :tagId")
    List<ProductGetDto> findProductsInTag(Long tagId);

    @Query("SELECT t FROM Tag t LEFT JOIN t.products p WHERE p.productId = :productId")
    List<Tag> findTagsFromProduct(Long productId);
}
