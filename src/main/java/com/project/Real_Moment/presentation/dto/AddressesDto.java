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
    public static class AddAddressRequest {
<<<<<<< HEAD:src/main/java/com/project/Real_Moment/presentation/dto/AddressesDto.java
=======
        private Long memberId;
>>>>>>> main:src/main/java/com/project/Real_Moment/presentation/dto/AddressDto.java
        private String name;
        private String address;
        private String detAddress;
        private Boolean isDefAddress;

<<<<<<< HEAD:src/main/java/com/project/Real_Moment/presentation/dto/AddressesDto.java
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
=======
        public AddAddressRequest(Long id) {
            this.memberId = id;
        }

//        public AddAddressRequest fromEntity(Addresses address) {
//            return AddAddressRequest.builder()
//                    .name(address.getName())
//                    .address(address.getAddress())
//                    .detAddress(address.getDetAddress())
//                    .isDefAddress(address.getIsDefAddress())
//                    .build();
//        }

        public Addresses toEntity(Long id) {
            return Addresses.builder()
                    .name(this.name)
                    .address(this.address)
                    .detAddress(this.detAddress)
                    .isDefAddress(this.isDefAddress)
                    .memberId(new AddAddressRequest(id))
                    .build();
>>>>>>> main:src/main/java/com/project/Real_Moment/presentation/dto/AddressDto.java
        }
    }
}
