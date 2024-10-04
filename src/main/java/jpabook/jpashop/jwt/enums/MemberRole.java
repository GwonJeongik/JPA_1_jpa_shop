package jpabook.jpashop.jwt.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {
    ADMIN("관리자"),
    USER("일반 회원");

    private final String role;
}
