package com.br.personniMoveis.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_cmp_table")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderCmp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_cmp_id")
    private Long orderCmpId;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "total_price")
    private Double totalPrice;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String status;

    private String txid;

    @OneToMany
    @JoinColumn(name = "order_cmp_id")
    private final List<OrderItemCmp> orderCmpItems = new ArrayList<>();

    @Override
    public int hashCode() {
        return Objects.hash(orderCmpId);
    }
}
