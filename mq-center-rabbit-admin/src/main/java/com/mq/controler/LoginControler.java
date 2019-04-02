package com.mq.controler;

import com.mq.common.util.ValueHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 功能：登录 controller
 * @author sunpeng
 * @date 2018
 */
@Slf4j
@Controller
@RequestMapping("/login")
public class LoginControler {

    @Inject
    private LoginService loginService;
    @Inject
    private SysUserRepository sysUserRepository;
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
            TbSysUser user = sysUserRepository.findOne(valueHolder.getUserIdHolder());
            request.setAttribute("uname", user != null ? user.getUname() : "");
            return "home";
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