package com.mq.controler;

import com.mq.common.util.JsonUtil;
import com.mq.data.to.request.ThirdPlatformBuildMqMsgRequest;
import com.mq.service.MqMsgManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @PostMapping(value= "/callbackTest")
    public void callbackTest(@RequestBody Object param) throws Exception {
        log.info("收到回调消息：" + JsonUtil.objectToJson(param));
    }
}