package com.project.shoppingmall.entity;


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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private List<Category> subCategory = new ArrayList<>();
}

// https://velog.io/@joshuara7235/JPA-%EC%82%AC%EC%9A%A9%ED%95%9C-%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC-%EA%B5%AC%ED%98%84-infinite-depth-01
// https://jie0025.tistory.com/329
// https://data-make.tistory.com/610