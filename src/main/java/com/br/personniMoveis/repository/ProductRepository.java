package com.br.personniMoveis.repository;

import com.br.personniMoveis.dto.product.get.ProductGetDto;
import com.br.personniMoveis.model.product.Product;
import com.br.personniMoveis.model.product.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    /**
     * Retorna todos os produtos que possuem a tag com o Id informado.
     *
     * @param tagId O id da tag que se deseja adquirir os produtos.
     * @return Todos os produtos que possuem a tag com o Id informado.
     */
    @Query("SELECT new com.br.personniMoveis.dto.product.get.ProductGetDto(" +
            "p.productId, p.name, p.value, p.quantity, p.editable, p.mainImg, p.description) " +
            "FROM Product p " +
            "JOIN p.tags t " +
            "WHERE t.tagId = :tagId")
    List<ProductGetDto> findProductsInTag(Long tagId);

    /**
     * Retorna todas as tags no produto indicado por id.
     *
     * @param productId Id do produto que se deseja adquirir as tags.
     * @return Todas as tags no produto indicado por id.
     */
    @Query("SELECT t FROM Tag t LEFT JOIN t.products p WHERE p.productId = :productId")
    List<Tag> findTagsFromProduct(Long productId);

    /**
     * Retorna os produtos mais recentemente inseridos. Se o endpoint no receber um valor de
     * quantidade, o default e retornar 4 produtos.
     *
     * @param amountOfProducts Quantidade de produtos recentemente insetidos que se deseja adquirir.
     * @return Os produtos mais recentemente inseridos.
     */
    @Query("SELECT p FROM Product p ORDER BY p.dtCreated DESC LIMIT :amountOfProducts")
    List<Product> getMostRecentProducts(Integer amountOfProducts);
}
