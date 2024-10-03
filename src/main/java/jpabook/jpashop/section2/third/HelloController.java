package jpabook.jpashop.section2.third;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 3. View 환경 설정
 * 	implementation 'org.springframework.boot:spring-boot-devtools' 라이브러리를 추가하면,
 * 	html 파일을 컴파일만 해주면 서버 재시작 없이, View 파일 변경이 가능하다.
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!");
        return "hello";
    }
}
