package com.project.shoppingmall.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class Category {

    @Id
    @GeneratedValue
    private Long categoryID;

    @Column
    private String category;


    @ManyToOne
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
}
