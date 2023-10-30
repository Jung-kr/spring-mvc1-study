package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {   // Get - 쿼리 파라미터, Post - HTML Form 2개 관련

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    @ResponseBody   // @Controller + String 이면 뷰리졸버가 ok라는 뷰를 찾기 때문에 response에 ok를 직접 넣기 위해 사용
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName,
                                 @RequestParam("age") int memberAge) {

        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username, // 변수명이 같으면 ("username") 생략 가능
                                 @RequestParam int age) {

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {    // 요청 파라미터 이름과 같으면 @RequestParam 생략 가능

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = true) String username, // 해당 요청 파라미터가 없으면 오류 (default)
                                       @RequestParam(required = false) Integer age) {   // 파라미터 없이 요창하면 age에 null이 들어가야 하는데 int형은 null값이 들어갈 수 없으므로 에러 발생

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
                                      @RequestParam(required = false, defaultValue = "-1") Integer age) {   // 값이 안들어오면 기본값으로 쓰겠다.(오류 발생 X)

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {   // Map으로 조회

        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
//    public String modelAttributeV1(@RequestParam String username, @RequestParam int age) {
//        HelloData helloData = new HelloData();
//        helloData.setUsername(username);
//        helloData.setAge(age);
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {   // 파라미터를 자동으로 HelloData에 매핑 해줌
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {   // @ModelAttribute 생략 가능
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}
