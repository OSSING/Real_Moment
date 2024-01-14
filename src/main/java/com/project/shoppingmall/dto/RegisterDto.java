package com.project.shoppingmall.dto;

import com.project.shoppingmall.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

public class RegisterDto {

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
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "아이디를 입력해주세요.")
        @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "아이디는 특수문자를 제외한 5~20자를 사용하세요.")
        String id;

        @NotBlank(message = "이메일 주소를 입력해주세요.")
        @Email(message = "올바른 이메일 주소를 입력해주세요.")
        String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\\\W)(?=\\\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        String password;

        @NotBlank(message = "성함을 입력해주세요.")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z]{2,20}", message = "성함은 한글과 영문을 포함한 2~20자를 사용하세요.")
        String name;

        @NotBlank(message = "휴대폰 번호를 입력해주세요.")
        @Pattern(regexp = "(01[016789])(\\\\d{3,4})(\\\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
        String tel;

        @NotBlank(message = "생년월일을 입력해주세요.")
        @Pattern(regexp = "^[0-9]{6}", message = "주민등록번호 앞 6자리를 입력해주세요.")
        Date birthDate;

        char gender;

        // Dto -> Entity
        public Member toEntity() {
            return Member.builder()
                    .id(id)
                    .email(email)
                    .password(password)
                    .name(name)
                    .tel(tel)
                    .birthDate(birthDate)
                    .gender(gender)
                    .build();
        }
    }



    @Getter
    // 회원가입 성공 시 응답할 JSON 데이터
    public static class Response {
        String id;

        // Entity -> Dto
        public Response(Member member) {
            this.id = member.getId();
        }
    }
}
