package com.br.personniMoveis.repository;

import com.br.personniMoveis.dto.ProductDto;
import com.br.personniMoveis.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//@SqlResultSetMapping(
//    name = "ProductDTOMapping",
//    classes = @ConstructorResult(
//        targetClass = ProductDto.class,
//        columns = {
//            @ColumnResult(name = "name"),
//            @ColumnResult(name = "value"),
//            @ColumnResult(name = "quantity"),
//            @ColumnResult(name = "editable"),
//            @ColumnResult(name = "imgUrl"),
//            @ColumnResult(name = "description")
//        }
//    )
//)
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

//    @Query("SELECT new ProductDTO(p.name, p.value, p.quantity, p.editable, p.imgUrl, p.description) FROM Product p")
//    Page<ProductDto> findAllProducts(Product product);
    
//    @Query("SELECT new ProductDTO(p.name, p.value, p.quantity, p.editable, p.imgUrl, p.description) FROM Product p")
//    List<Product> findAllProducts();
}
