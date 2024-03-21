package com.project.Real_Moment.presentation.dto;



import com.project.Real_Moment.domain.enumuration.Gender;
import com.project.Real_Moment.domain.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MemberDto {

    // 회원가입 시 요청받을 JSON 데이터

    /**
     * static 내부 클래스로 선언하게 된다면 메모리 누수 원인을 예방할 수 있다.
     * 클래스의 각 인스턴스당 더 적은 메모리를 사용하게 된다.
     * static이 아닌 멤버 클래스는 바깥 인스턴스와 암묵적으로 연결이 되기 때문에
     * 바깥 인스턴스 없이는 생성할 수 없다.
     * 두 클래스 관계는 멤버 클래스의 인스턴스 안에 만들어지며, 메모리를 차지하게 되고 생성도 느리다.
     * 즉 멤버 클래스에서 바깥 인스턴스에 접근할 일이 없다면 무조건 static을 붙여 정적 멤버 클래스로 만들어주자.
     * https://dev-coco.tistory.com/138
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RegisterRequest {
        @NotBlank(message = "아이디를 입력해주세요.")
        @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "아이디는 특수문자를 제외한 5~20자를 사용하세요.")
        private String loginId;

        @NotBlank(message = "이메일 주소를 입력해주세요.")
        @Email(message = "올바른 이메일 주소를 입력해주세요.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\\\W)(?=\\\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String loginPassword;

        @NotBlank(message = "성함을 입력해주세요.")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z]{2,20}", message = "성함은 한글과 영문을 포함한 2~20자를 사용하세요.")
        private String name;

        @NotBlank(message = "휴대폰 번호를 입력해주세요.")
        @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "올바른 휴대폰 번호를 입력해주세요.")
        private String tel;

        @NotBlank(message = "생년월일을 입력해주세요.")
        private LocalDate birthDate;

        private Gender gender;

        // 회원가입 시 요청받은 Dto -> Entity 변환
//        public Member toEntity() {
//            return Member.builder()
//                    .loginId(loginId)
//                    .email(email)
//                    .loginPassword(loginPassword)
//                    .name(name)
//                    .tel(tel)
//                    .birthDate(birthDate)
//                    .gender(gender)
//                    .build();
//        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    // 회원가입 성공 시 응답할 JSON 데이터
    public static class RegisterResponse {

        private String loginId;

        // Entity -> Dto
        public static RegisterResponse toDto(Member member) {
            if (member == null) return null;

            return RegisterResponse.builder()
                    .loginId(member.getLoginId())
                    .build();
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberLoginDto {

        @NotBlank(message = "아이디를 입력해주세요.")
        private String loginId;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String loginPassword;
    }

//    @Getter
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class OrdersListDto {
//
//        private int price;
//        private String name;
//        private String address;
//        private String detAddress;
//        private String request;
//        private String tel;
//        private String status;
//        private String refundText;
//
//        // Entity -> DTO
//        public OrdersListDto(Order order) {
//            price = order.getPrice();
//            name = order.getName();
//            address = order.getAddress();
//            detAddress = order.getDetAddress();
//            request = order.getRequest();
//            tel = order.getTel();
//            status = order.getStatus();
//            refundText = order.getRefundText();
//        }
//    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PasswordChangeRequest {

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\\\W)(?=\\\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String loginPassword;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberInfoUpdateResponse {
        private String loginId;
        private String email;
        private String name;
        private Gender gender;
        private LocalDate birthDate;

        public MemberInfoUpdateResponse(Member member) {
            loginId = member.getLoginId();
            email = member.getEmail();
            name = member.getName();
            gender = member.getGender();
            birthDate = member.getBirthDate();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailChangeRequest {

        @NotBlank(message = "이메일 주소를 입력해주세요.")
        @Email(message = "올바른 이메일 주소를 입력해주세요.")
        private String email;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NameChangeRequest {

        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z]{2,20}", message = "성함은 한글과 영문을 포함한 2~20자를 사용하세요.")
        private String name;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BirthDateChangeRequest {

        @NotBlank(message = "생년월일을 입력해주세요.")
        private LocalDate birthDate;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberListWrapper {
        private List<MemberList> memberList;
        private int totalPage;
        private int nowPage;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberList {
        private Long memberId;
        private GradeDto.GradeResponse grade;
        private String loginId;
        private String name;
        private Boolean isDelete;
        private LocalDateTime createdDate;

        public MemberList(Member member) {
            memberId = member.getId();

            grade = new GradeDto.GradeResponse(member.getGradeId());
            loginId = member.getLoginId();
            name = member.getName();
            isDelete = member.isDelete();
            createdDate = member.getCreatedDate();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class memberDet {
        private Long memberId;
        private GradeDto.GradeResponse grade;
        private String loginId;
        private String email;
        private String name;
        private String tel;
        private String gender;
        private int point;
        private boolean isDelete;
        private LocalDateTime recentlyLogin;
        private LocalDateTime createdDate;

        public memberDet(Member member) {
            memberId = member.getId();
            grade = new GradeDto.GradeResponse(member.getGradeId());
            loginId = member.getLoginId();
            email = member.getEmail();
            name = member.getName();
            tel = member.getTel();
            gender = String.valueOf(member.getGender());
            point = member.getPoint();
            isDelete = member.isDelete();
            recentlyLogin = member.getRecentlyLogin();
            createdDate = member.getCreatedDate();
        }
    }
}
