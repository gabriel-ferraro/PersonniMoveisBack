package com.br.personniMoveis.model.product;

import com.br.personniMoveis.model.ProductImg;
import com.br.personniMoveis.model.category.Category;
import com.br.personniMoveis.model.user.OrderItem;
import com.br.personniMoveis.model.user.UserEntity;
import com.br.personniMoveis.utils.CustomProductImgDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Mapeamento ORM para produto.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id")
    private Long productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Boolean editable;

    @Column(name = "main_img", length = 1000)
    private String mainImg;

    @Column(length = 1000)
    private String description;

    @Column(name = "dt_created")
    private LocalDateTime dtCreated;

    @Column(name = "dt_updated")
    private LocalDateTime dtUpdated;

    /**
     * Flag para disponibilidade do produto no catálogo (se produto ainda está sendo vendido). Obs: o produto pode estar
     * visível no catálogo mas não disponível para venda (available = false).
     */
    @Column
    private Boolean available;

    /**
     * Flag para fazer exclusão lógica - determina se produto foi removido:
     * (não pode ser mais comprado, não parece mais em pesquisas).
     */
    @Column
    private Boolean isRemoved;

    /**
     * Usado somente como referência para uso no front. Não é utilizado para controle da relação entre produto e categoria
     * no back.
     */
    @Column(name = "category_id")
    private Long categoryId;

    /**
     * O produto pode ter imagens secundárias, que aparecem na página do produto single como imagens "adicionais".
     */
    @OneToMany
    @JoinColumn(name = "product_id")
    @JsonDeserialize(using = CustomProductImgDeserializer.class)
    private Set<ProductImg> secondaryImages = new HashSet<>();

    /**
     * Details são campos descritivos do produto, exemplo: peso do produto - A cadeira X é leve e tem só x kg.
     */
    @OneToMany
    @JoinColumn(name = "product_id")
    private Set<Detail> details;

    /**
     * Um móvel pode ter somente um material (material é o que compõe a maior parte do produto, como madeira de algum tipo, metal, etc).
     */
    @ManyToMany
    @JoinTable(name = "product_material", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "material_id"))
    private Set<Material> materials;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Set<Section> sections;

    /**
     * As tags podem estar em produtos diferentes, não pertencem a um produto específico, exemplos de tags: escritório, cozinha, sala de estar, etc.
     */
    @ManyToMany
    @JoinTable(name = "product_tag", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    /**
     * Produto pode ser de SOMENTE UMA categoria, ou de nehuma, como: caderias, armários, mesas, etc...
     */
    @ManyToOne
    @JoinColumn(name = "id")
    private Category category;

    /**
     * Mantém controle da lista de espera do usuário (produtos que notificam usuário via e-mail
     * ao retornar à disponibilidade).
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "productWaitingList")
    private final List<UserEntity> users = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "product_order_item", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "order_item_id"))
    private final List<OrderItem> orderItems = new ArrayList<>();

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
