package jpabook.jpashop.section1.third;

import lombok.Getter;
import lombok.Setter;

/**
 * 3. View 환경 설정
 * 	implementation 'org.springframework.boot:spring-boot-devtools' 라이브러리를 추가하면,
 * 	html 파일을 컴파일만 해주면 서버 재시작 없이, View 파일 변경이 가능하다.
 */
@Getter
@Setter
public class Hello {

    private String message;
}
