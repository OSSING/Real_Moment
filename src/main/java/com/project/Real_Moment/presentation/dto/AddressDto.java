package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.member.entity.Addresses;
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
    public static class AddAddressRequest {
        private String name;
        private String address;
        private String detAddress;
        private Boolean isDefAddress;

//        public AddAddressRequest fromEntity(Addresses address) {
//            return AddAddressRequest.builder()
//                    .name(address.getName())
//                    .address(address.getAddress())
//                    .detAddress(address.getDetAddress())
//                    .isDefAddress(address.getIsDefAddress())
//                    .build();
//        }

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
}
