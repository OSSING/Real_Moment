package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.member.entity.Addresses;
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
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddAddressRequest {
        private String name;
        private String address;
        private String detAddress;
        private Boolean isDefAddress;

        public AddAddressRequest(Addresses addresses) {
            name = addresses.getName();
            address = addresses.getAddress();
            detAddress = addresses.getDetAddress();
            isDefAddress = addresses.getIsDefAddress();
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddAddressResponse {
        private Long addressId;
        private String name;
        private String address;
        private String detAddress;
        private Boolean isDefAddress;

        public AddAddressResponse(Addresses addresses) {
            addressId = addresses.getAddressId();
            name = addresses.getName();
            address = addresses.getAddress();
            detAddress = addresses.getDetAddress();
            isDefAddress = addresses.getIsDefAddress();
        }
    }
}
