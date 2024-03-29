package com.br.personniMoveis.model.user;

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
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    private Long orderId;

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
    @JoinColumn(name = "order_id")
    private final List<OrderItem> orderItems = new ArrayList<>();

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}
