package org.coreBanking.controller;

import org.coreBanking.service.TestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    // 테스트 초기 환경 세팅
    @PostMapping("/init")
    public void init(int seed) {
        testService.init(seed);
    }

    // 시간 변경
    @PostMapping("/clock")
    public void clock(int hour, int minute, int second) {
        testService.setClock(hour, minute, second);
    }

}
