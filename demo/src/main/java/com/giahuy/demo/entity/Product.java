package com.giahuy.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    private Category category;

}
