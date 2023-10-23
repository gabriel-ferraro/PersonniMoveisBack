package com.br.personniMoveis.dto.product;

import com.br.personniMoveis.model.ProductImg;
import com.br.personniMoveis.model.product.Detail;
import com.br.personniMoveis.model.product.Section;
import com.br.personniMoveis.model.user.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPutDto {

    private String name;
    private Double value;
    private Long quantity;
    private Boolean editable;
    private String mainImg;
    private String description;
    private LocalDateTime dtCreated;
    private LocalDateTime dtUpdated;
    private Boolean available;
    private Long categoryId;
    private Set<ProductImg> secondaryImages;
    private Set<Detail> details;
    private Set<Section> sections;
    private List<OrderItem> orderItems;
}

