package com.project.Real_Moment.presentation.dto;

import com.project.Real_Moment.domain.entity.Admin;
import com.project.Real_Moment.domain.enumuration.AdminAuthority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class AdminDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CheckIdDuplicate {
        private String loginId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminListWrapper {
        private List<AdminList> adminList;
        private int totalPage;
        private int nowPage;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminList {
        private Long adminId;
        private String loginId;
        private String email;
        private String name;
        private String roles;

        public AdminList(Admin admin) {
            adminId = admin.getId();
            loginId = admin.getLoginId();
            email = admin.getEmail();
            name = admin.getName();
            roles = AdminAuthority.getDescription(String.valueOf(admin.getRoles()));
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminInfo {
        private String email;
        private String name;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminRolesInfo {
        private Long adminId;
        private String roles;
    }
}
