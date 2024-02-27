package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.member.entity.Addresses;
import com.project.Real_Moment.domain.member.entity.Member;
import lombok.*;

public class AddressesDto {

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddressListResponse {
        private Long addressId;
        private String name;
        private String address;
        private String detAddress;
        private Boolean isDefAddress;

        public AddressListResponse(Addresses addresses) {
            addressId = addresses.getAddressId();
            name = addresses.getName();
            address = addresses.getAddress();
            detAddress = addresses.getDetAddress();
            isDefAddress = addresses.getIsDefAddress();
        }
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SaveAddressRequest {
        private String name;
        private String address;
        private String detAddress;
        private Boolean isDefAddress;

        public Addresses toEntity(Member member) {
            return Addresses.builder()
                    .name(this.name)
                    .address(this.address)
                    .detAddress(this.detAddress)
                    .isDefAddress(this.isDefAddress)
                    .memberId(member)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddressRequest {
        private Long addressId;
        private String name;
        private String address;
        private String detAddress;
        private Boolean isDefAddress;
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddressResponse {
        private String name;
        private String address;
        private String detAddress;
        private Boolean isDefAddress;

        public AddressResponse(Addresses address) {
            this.name = address.getName();
            this.address = address.getAddress();
            this.detAddress = address.getDetAddress();
            this.isDefAddress = address.getIsDefAddress();
        }
    }
}