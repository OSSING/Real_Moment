package com.project.shoppingmall.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Reviews {

    @Id
    @GeneratedValue
    private Long reviewId;

    @ManyToOne
    private Users userId;

    @ManyToOne
    private Items itemId;

    private String title;

    private String content;

    private int star;

    private LocalDateTime writeDate;

    private LocalDateTime editedDate;
}
