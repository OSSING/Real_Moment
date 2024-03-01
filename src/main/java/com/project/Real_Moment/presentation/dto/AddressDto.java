package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.member.entity.Address;
import com.project.Real_Moment.domain.member.entity.Member;
import lombok.*;

public class AddressDto {

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddressListResponse {
        private Long addressId;
        private String name;
        private String tel;
        private String mainAddress;
        private String detAddress;
        private Boolean isDefAddress;

        public AddressListResponse(Address address) {
            addressId = address.getAddressId();
            name = address.getName();
            tel = address.getTel();
            this.mainAddress = address.getMainAddress();
            detAddress = address.getDetAddress();
            isDefAddress = address.getIsDefAddress();
        }
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SaveAddressRequest {
        private String name;
        private String tel;
        private String mainAddress;
        private String detAddress;
        private Boolean isDefAddress;

        public Address toEntity(Member member) {
            return Address.builder()
                    .name(this.name)
                    .tel(this.tel)
                    .mainAddress(this.mainAddress)
                    .detAddress(this.detAddress)
                    .isDefAddress(this.isDefAddress)
                    .memberId(member)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddressRequest {
        private Long addressId;
        private String name;
        private String tel;
        private String mainAddress;
        private String detAddress;
        private Boolean isDefAddress;
    }
}