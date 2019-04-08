package com.mq.controler;

import com.mq.common.data.base.PageList;
import com.mq.common.data.response.StringResponse;
import com.mq.data.entity.TbMqMsg;
import com.mq.data.to.request.MqMsgSearchRequest;
import com.mq.service.MqMsgManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * 功能：消息管理 controller
 * @author sunpeng
 * @date 2019
 */
@Slf4j
@Controller
@RequestMapping("/user/mqMsgManage")
public class MqMsgManageControler {
    @Inject
    private MqMsgManageService mqMsgManageService;

    @GetMapping(value = "/toMqMsgManage")
    public String toMqMsgManage() {
        return "menu/mqMsgManage";
    }

    @ResponseBody
    @PostMapping(value= "/search")
    public PageList<TbMqMsg> search(@RequestBody MqMsgSearchRequest param) throws Exception {
        return mqMsgManageService.search(param);
    }

    @ResponseBody
    @GetMapping(value= "/queryDetail")
    public TbMqMsg queryDetail(@RequestParam Long id) throws Exception {
        return mqMsgManageService.queryDetail(id);
    }

    @ResponseBody
    @PostMapping(value= "/handBuildMqMsg")
    public void handBuildMqMsg(@RequestBody TbMqMsg mqMsg) throws Exception {
        mqMsgManageService.handBuildMqMsg(mqMsg);
    }

    @ResponseBody
    @GetMapping(value= "/handPushMqMsg")
    public StringResponse handPushMqMsg(@RequestParam Long id) throws Exception {
        String resStr = mqMsgManageService.handPushMqMsg(id);
        return new StringResponse(resStr);
    }
}