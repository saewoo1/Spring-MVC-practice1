package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RequestBodyJsonController {
    private ObjectMapper objectMapper = new ObjectMapper(); // JSON 읽어오기 위해서

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream(); // HTTP body 내용 내놔
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        // copyToString 이용해서 inputStream 을 UTF-8로 인코딩한 String 데이터로 변환

        log.info("messageBody={}", messageBody); // JSON 타입으로 찍혀
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        // JSON 형식의 애들을 readValue 통해서 helloData 양식에 맞게 넣어줘

        log.info("helloData={}", helloData);
        response.getWriter().write("ok");
    }

    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        // @RequestBody -> inputStream 갖다치우고, HTTP body 내놔
        log.info("messageBody={}", messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("helloData={}", helloData);
        return "ok";
    }

    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {

        // objectMapper.readValue 얘도 필요 없고, 바로 @RequestBody HelloData(객체명) 변수명 -> 알아서 넣어줌;;
        log.info("helloData={}", helloData);
        return "ok";
    }

    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(RequestEntity<HelloData> helloData) throws IOException {

        /*
        * 신기~! getBody를 사용하지 않고 바로 helloData 를 뽑아보면, requestHeader, Body 모두 보인다!
        * */
        log.info("helloData={}", helloData);
        HelloData body = helloData.getBody();
        log.info("body={}", body);
        return "ok";
    }

    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) {
        log.info("helloData={}", helloData);

        // JSON 타입으로 나가여!! JSON -> 객채 -> JSON
        return helloData;
    }
}
