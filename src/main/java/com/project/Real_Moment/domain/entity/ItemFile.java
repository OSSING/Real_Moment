package com.project.Real_Moment.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemFile {

    /**
     * 상품 추가에서 이미지 추가 시 변수 하나 '0'값으로 생성해서 '+1'씩 지정하며 이미지 추가
     * 상품 추가 시 메인 이미지는 최소 1개 이상 등록
     * 대포 이미지는 이미지 교체, 순서 교체, 이미지를 '0'으로 추가 가능
     * 이미지 응답 시 number 오름차순으로 응답
     * 이미지 삭제 시 삭제된 number 뒤로 number '-1' (메인의 대포 이미지는 삭제 불가)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s3_file_id")
    private S3File s3FileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item itemId;

    @Column(name = "main_or_sub")
    private String mainOrSub;

    private int number;
}
