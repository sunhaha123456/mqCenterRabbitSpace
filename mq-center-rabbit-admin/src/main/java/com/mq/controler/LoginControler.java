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
 * 功能：登录 controller
 * @author sunpeng
 * @date 2019
 */
@Slf4j
@Controller
@RequestMapping("/login")
public class LoginControler {

    @Inject
    private LoginService loginService;
    @Inject
    private TbUserRepository tbUserRepository;
    @Inject
    private RedisRepositoryCustom redisRepositoryCustom;
    @Inject
    private ValueHolder valueHolder;

    @GetMapping(value = "/toLogin")
    public String toLogin() {
        return "login";
    }

    @GetMapping(value = "/toSuccess")
    public String toSuccess(HttpServletRequest request) throws Exception {
        if (loginService.tokenValidate(request)) {
            Optional<TbUser> tbUserOptional = tbUserRepository.findById(valueHolder.getUserIdHolder());
            if (tbUserOptional.isPresent()) {
                TbUser user = tbUserOptional.get();
                request.setAttribute("uname", user != null ? user.getUname() : "");
                return "home";
            }
        }
        return "login";
    }

    @GetMapping(value = "/out")
    public String out(HttpServletRequest request) throws Exception {
        if (loginService.tokenValidate(request)) {
            redisRepositoryCustom.delete(RedisKeyUtil.getRedisUserInfoKey(valueHolder.getUserIdHolder()));
        }
        return "login";
    }

    @ResponseBody
    @PostMapping(value = "/verify")
    public Map verify(@RequestBody @Validated(UserLoginRequest.BaseInfo.class) UserLoginRequest param) throws Exception {
        return loginService.verify(param);
    }
}