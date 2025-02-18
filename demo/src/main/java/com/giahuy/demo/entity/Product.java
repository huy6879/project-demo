package com.giahuy.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    Integer id;

    @Basic(optional = false)
    @Column(name = "name")
    String name;

    @Column(name = "price")
    Integer price;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "unit")
    Integer unit;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category_id;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<OrderDetails> orderDetails;  // Mối quan hệ với OrderDetails
}
