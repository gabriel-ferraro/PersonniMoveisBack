package com.br.personniMoveis.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "total_price")
    private Double totalPrice;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "id")
    @JsonIgnore
    private UserEntity user;

    @Column(name = "is_cmp")
    private Boolean isCmp;

    private String status;

    @OneToMany
    @JoinColumn(name = "order_id")
    private final List<OrderItem> orderItems = new ArrayList<>();

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}
