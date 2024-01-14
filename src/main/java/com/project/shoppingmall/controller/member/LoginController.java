package com.project.shoppingmall.controller.member;

import com.project.shoppingmall.dto.RegisterDto;
import com.project.shoppingmall.entity.Member;
import com.project.shoppingmall.repository.AdminsRepository;
import com.project.shoppingmall.repository.MemberRepository;
import com.project.shoppingmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AdminsRepository adminsRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    // 회원가입 도중 id 중복체크 있다면 true 없다면 false 반환
    @GetMapping("/user/{id}/exists")
    public ResponseEntity<Boolean> checkIdDuplicate(@PathVariable("id") String id) {
        log.info("controller.id = {}", id);
//        return ResponseEntity.status(HttpStatus.OK).body(memberService.checkIdDuplicate(id));
        return ResponseEntity.ok(memberService.checkIdDuplicate(id));
    }


    // 회원가입 요청
    @PostMapping("/user/join")
    public ResponseEntity<Member> join(@RequestBody RegisterDto.RegisterRequest dto) {
        log.info("RegisterDto.toString() = {}", dto.toString());

        // Service단에 요청받은 회원 정보를 전송 후 반환받은 dto를 클라이언트에게 전송
        return ResponseEntity.ok(memberService.save(dto));
    }
}
