package jpabook.jpashop.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jpabook.jpashop.jwt.enums.MemberRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    /* JWT 토큰 만료 시간 */
    public final static String BEARER_PREFIX = "Bearer ";

    private final Long EXPIRATION_TIME = 60 * 60 * 1000L;

    /* JWT SECRET KEY 디코딩된 값 */
    private final Key secretKey;

    /* JWT SECRET KEY 디코딩한 것을 생성자로 주입 */
    public JwtUtil(@Value(value = "${jwt.secret.key}") String secretKey) {
        byte[] decodeSecretKey = Base64.getDecoder().decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(decodeSecretKey);
        log.info("secretKey={}", this.secretKey);
    }

    /* JWT 생성 */
    public String createToken(Long memberId, MemberRole role) {
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(String.valueOf(memberId)) // JWT 토큰의 주체 설정 [id]
                        .claim("role", role.getRole()) //`JWT`에 추가로 넣어야하는 정보 [회원의 역할]
                        .setIssuedAt(new Date()) //발급일
                        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))//만료시간
                        .signWith(secretKey, SignatureAlgorithm.HS256) //`HS256`을 이용하여 암호화 알고리즘
                        .compact();
    }

    /* JWT 사용자 id 조회 */
    public String extractMemberId(String token) {
        return extractClaims(token).getSubject();
    }

    /* JWT 사용자 Role 조회 */
    public String extractMemberRole(String token) {
        return (String) extractClaims(token).get("role");
    }

    /* JWT 유효성 검증 */
    public boolean isTokenValid(String token, String memberId) {
        String extractMemberId = extractMemberId(token); //토큰에서 사용자 이름 추출

        if (!extractMemberId.equals(memberId)) {
            log.error("회원 아이디가 유효하지 않습니다.");
            return false;
        }

        if (isTokenExpired(token)) {
            log.error("기간이 만료되었습니다.");
            return false;
        }

        return true;
    }

    /* JWT 만료 검증 */
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }


    /* Claims 추출 메서드 */
    private Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder() //JWT 파서 빌드
                    .setSigningKey(secretKey) //secretKey 설정
                    .build() //빌드 완료
                    .parseClaimsJws(token) //토큰 파싱
                    .getBody(); //Claims 추출
        } catch (Exception e) {
            log.error("Claims 추출 메서드 오류", e);
            throw new RuntimeException("Claims 추출 메서드 오류", e);
        }
    }


    /* `JWT`를 `Cookie`에 저장 -> 사용하지 않을 거지만 일단 코드만 남겨둠 */
/*
    public void addJwtToCookie(String token, HttpServletResponse response) {
        try {
            token = URLEncoder.encode(token, "utf-8").replace("\\+", "%20");//`Cookie`에는 공백이 있으면 안 된다.

            Cookie cookie = new Cookie("authorization", token);
            cookie.setPath("/");

            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            log.error("addJwtToCookie error", e);
        }
    }
*/

    //`Cookie`에 들어있는 `JWT`토큰을 subString
    //JWT 검증
}
