package com.project.Real_Moment.auth.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // 요구되는 권한을 갖고 있지 않은 경우 403 Forbidden Error를 반환
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}

// presentation - controller
// application - service
// domain - entity, repository
// infra - persistence(repositoryImpl), config
// member, admin, wishList, carts, item, order, review, QA, announcement