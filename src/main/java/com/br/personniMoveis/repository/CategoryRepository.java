package com.br.personniMoveis.repository;

import com.br.personniMoveis.dto.product.get.ProductGetDto;
import com.br.personniMoveis.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    @Query(value = """
            SELECT new com.br.personniMoveis.dto.product.get.ProductGetDto(p.productId, p.name, p.value, p.quantity, p.editable, p.mainImg, p.description)
            FROM Product p
            LEFT JOIN p.category c
            WHERE c.id = :id AND p.isRemoved = FALSE
            """)
    List<ProductGetDto> getAllProductsInCategory(@Param("id") Long categoryId);

    /**
     * Encontra as categorias vigentes (não removidas lógicamente).
     */
    List<Category> findByIsRemovedFalse();
}
