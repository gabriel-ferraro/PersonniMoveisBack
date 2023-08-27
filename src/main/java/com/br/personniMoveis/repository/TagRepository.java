package com.br.personniMoveis.repository;

import com.br.personniMoveis.model.product.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findTagsByProductsProductId(Long productId);
}
