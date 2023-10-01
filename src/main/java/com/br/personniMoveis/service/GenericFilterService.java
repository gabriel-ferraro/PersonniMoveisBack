//package com.br.personniMoveis.service;
//
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.stereotype.Service;
//
///**
// * Serviço genérico para realização de filtros.Gerencia filtros, paginação e
// * ordenação de recursos.
// *
// * @param <T> Tipo genérico para "entidade" que sera filtrada.
// */
//@Service
//public class GenericFilterService<T> {
//
//    private final JpaSpecificationExecutor<T> jpaSpecificationExecutor;
//
//    @Autowired
//    public GenericFilterService(JpaSpecificationExecutor<T> jpaSpecificationExecutor) {
//        this.jpaSpecificationExecutor = jpaSpecificationExecutor;
//    }
//
//    /**
//     * Realiza consulta de dados de uma entidade com base nos filtros recebidos.
//     *
//     * @param filters Mapa de itens para filtro.
//     * @param page Quantidade de páginas para realizar paginação.
//     * @param size Quantidade de itens por página.
//     * @return O reultado da consulta com base nos filtros recebidos.
//     */
//    public Page<T> findFilteredEntity(Map<String, Object> filters, int page, int size) {
//        // Constroi o objeto de paginação.
//        Pageable pageable = PageRequest.of(page, size);
//        // Adquire a especificação da consulta.
//        Specification<T> specification = buildSpecification(filters);
//        // Retorna o resultado da consulta filtrada, paginada e ordenada.
//        return jpaSpecificationExecutor.findAll(specification, pageable);
//    }
//
//    /**
//     * Retorna a especificação da busca com base nos filtros informados. Valores
//     * preenchidos na busca se tornam parte do filtro, vazios são ignorados.
//     *
//     * @param filters Mapa de atributos para criar o filtro de busca.
//     * @return retorna a especificação da busca com base nos filtros.
//     */
//    private Specification<T> buildSpecification(Map<String, Object> filters) {
//        // Retorna a especificação de busca com base nos itens do filtro.
//        return (root, query, criteriaBuilder) -> {
//            // Declara uma lista dos predicados que serão usados para busca.
//            List<Predicate> predicates = new ArrayList<>();
//            // Percorre o mapa de filtros.
//            for (String key : filters.keySet()) {
//                // Adquire o valor mapeado para a chave atual.
//                Object value = filters.get(key);
//                // Verifica se o atributo existe na entidade (Product) antes de adicioná-lo aos predicados.
//                if (isAttributePresent(root, key) && value != null) {
//                    predicates.add(criteriaBuilder.equal(root.get(key), value));
//                }
//            }
//            // Retorna uma especificação de filtros para realização da busca.
//            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
//        };
//    }
//
//    /**
//     * Método para verificar se um atributo está presente na entidade.
//     *
//     * @param root Referência à entidade.
//     * @param attributeName Nome do atributo de filtro.
//     * @return Valida se atributo existe na entidade.
//     */
//    private boolean isAttributePresent(Root<T> root, String attributeName) {
//        try {
//            root.get(attributeName);
//            return true;
//        } catch (IllegalArgumentException e) {
//            return false;
//        }
//    }
//}
