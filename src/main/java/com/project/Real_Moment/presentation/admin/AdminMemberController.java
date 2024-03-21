package com.project.Real_Moment.presentation.admin;

import com.project.Real_Moment.application.admin.AdminMemberService;
import com.project.Real_Moment.presentation.dto.CondDto;
import com.project.Real_Moment.presentation.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    @GetMapping("/admin/memberList")
    public ResponseEntity<MemberDto.MemberListWrapper> getMemberList(@RequestParam(value = "memberSort", required = false) String memberSort,
                                                   @RequestParam(value = "loginId", required = false) String loginId,
                                                   @RequestParam(value = "gradeId", required = false) Long gradeId,
                                                   @RequestParam(value = "isDelete", required = false) Boolean isDelete,
                                                   @RequestParam("nowPage") int nowPage) {
        CondDto.MemberListCond dto = new CondDto.MemberListCond(memberSort, loginId, gradeId, isDelete, nowPage);
        return ResponseEntity.ok().body(adminMemberService.getMemberList(dto));
    }


    @GetMapping("/admin/member")
    public ResponseEntity<MemberDto.memberDet> getMemberDet(@RequestParam("memberId") Long memberId) {
        return ResponseEntity.ok().body(adminMemberService.getMemberDet(memberId));
    }
}
