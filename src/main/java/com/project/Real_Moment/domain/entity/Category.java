package com.project.Real_Moment.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category")
    private Category parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
}

// https://velog.io/@joshuara7235/JPA-%EC%82%AC%EC%9A%A9%ED%95%9C-%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC-%EA%B5%AC%ED%98%84-infinite-depth-01
// https://jie0025.tistory.com/329
// https://data-make.tistory.com/610