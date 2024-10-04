package jpabook.jpashop.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import static jpabook.jpashop.jwt.JwtUtil.BEARER_PREFIX;
import static jpabook.jpashop.jwt.enums.MemberRole.ADMIN;
import static jpabook.jpashop.jwt.enums.MemberRole.USER;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");

        if (isAuthorizationInvalid(authorizationHeader)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String jwt = authorizationHeader.substring(7); //"Bearer " 제외한 JWT 추출
        String memberId = jwtUtil.extractMemberId(jwt); //`JWT`에서 회원 이름 추출
        String role = jwtUtil.extractMemberRole(jwt); //`JWT`에서 회원 역할 추출

        if (isJwtInvalid(memberId, jwt)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        if (role.equals(ADMIN.getRole())) {
            log.info("member role={}", role);
        }

        if (role.equals(USER.getRole())) {
            log.info("member role={}", role);
        }

        return true;
    }

    /* JWT 유효성 검증 */
    private boolean isJwtInvalid(String memberId, String jwt) {
        return !(StringUtils.hasText(memberId) && jwtUtil.isTokenValid(jwt, memberId));
    }

    /* Authorization 헤더 검증 메서드 */
    private static boolean isAuthorizationInvalid(String authorizationHeader) {
        return !(StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(BEARER_PREFIX));
    }
}
