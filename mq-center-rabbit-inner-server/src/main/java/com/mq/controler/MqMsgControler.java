package com.mq.controler;

import com.mq.data.to.request.ThirdPlatformBuildMqMsgRequest;
import com.mq.service.MqMsgManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@Slf4j
@RestController
@RequestMapping("/mqMsg")
public class MqMsgControler {

    @Inject
    private MqMsgManageService mqMsgManageService;

    @PostMapping(value= "/thirdPlatformBuildMqMsg")
    public void thirdPlatformBuildMqMsg(@RequestBody ThirdPlatformBuildMqMsgRequest param) throws Exception {
        mqMsgManageService.thirdPlatformBuildMqMsg(param);
    }
}