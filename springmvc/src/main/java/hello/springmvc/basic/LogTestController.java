package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {
//    private final Logger log = LoggerFactory.getLogger(LogTestController.class);

    @GetMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        // 어느 쓰레드에서, 어떤 컨트롤러가 해당 로그를 호출했을까 다 보여줘 -> 밑으로 갈 수록 심각도 업
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info("info log={}", name);
        log.warn("warn log={}", name);
        log.error("error log={}", name);

        return "ok";
    }
}
