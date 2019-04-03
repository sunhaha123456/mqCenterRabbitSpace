package com.mq.controler;

import com.mq.common.repository.RedisRepositoryCustom;
import com.mq.common.util.RedisKeyUtil;
import com.mq.common.util.ValueHolder;
import com.mq.data.entity.TbUser;
import com.mq.data.to.request.UserLoginRequest;
import com.mq.dbopt.repository.TbUserRepository;
import com.mq.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

/**
 * 功能：消息管理 controller
 * @author sunpeng
 * @date 2019
 */
@Slf4j
@Controller
@RequestMapping("/user/mqMsgManage")
public class MqMsgManageControler {

    @GetMapping(value = "/toMqMsgManage")
    public String toMqMsgManage() {
        return "menu/mqMsgManage";
    }
}