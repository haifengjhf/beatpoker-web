package com.jhf.beatpoker.web.controller;

import com.jhf.beatpoker.web.common.response.EnumStatusCode;
import com.jhf.beatpoker.web.common.response.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connectivityTest")
public class AppConnectivityTestController {

    @GetMapping("/ping")
    public ResponseBody<String> knock() {
        return new ResponseBody<>(EnumStatusCode.SUCCESS);
    }
}
