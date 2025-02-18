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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    Integer id;
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;

    @JsonIgnore
    @OneToMany(mappedBy = "category_id", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Product> products;
}
