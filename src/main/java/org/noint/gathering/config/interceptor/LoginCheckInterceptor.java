package org.noint.gathering.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.noint.gathering.domain.member.exception.MemberException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import static org.noint.gathering.domain.member.enums.MemberExceptionBody.FORBIDDEN;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {
    private final HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object memberId = session.getAttribute("memberId");
        if (ObjectUtils.isEmpty(memberId)) {
            log.warn("로그인 세션 정보 없음.");
            throw new MemberException(FORBIDDEN);
        }
        
        request.setAttribute("memberId", memberId);
        return true;
    }
}
