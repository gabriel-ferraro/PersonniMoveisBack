package com.br.personniMoveis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client_order")
public class ClientOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(name = "generated_at")
    private Instant generatedAt;
    
    // Por padrão, o pedido de um cliente não precisa ser aprovado.
    @Builder.Default
    private Boolean needApprovement = false;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserEntity clientOrder;
    
    @JsonIgnore
    @OneToMany(mappedBy = "clientOrder")
    @Setter(AccessLevel.NONE)
    private ArrayList<Product> products = new ArrayList<>();
    
}
