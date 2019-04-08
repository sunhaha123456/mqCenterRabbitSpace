package com.mq.service.impl;

import com.mq.common.data.base.PageList;
import com.mq.common.data.response.ResponseResultCode;
import com.mq.common.exception.BusinessException;
import com.mq.common.util.*;
import com.mq.data.constant.RabbitMqConstant;
import com.mq.data.entity.TbMqMsg;
import com.mq.data.entity.TbMqMsgPushReleation;
import com.mq.data.entity.TbUser;
import com.mq.data.enums.RequestPushPlatformEnum;
import com.mq.data.to.request.MqMsgSearchRequest;
import com.mq.data.to.request.ThirdPlatformBuildMqMsgRequest;
import com.mq.dbopt.mapper.TbMqMsgMapper;
import com.mq.dbopt.mapper.TbMqMsgPushReleationMapper;
import com.mq.dbopt.repository.TbMqMsgRepository;
import com.mq.dbopt.repository.TbUserRepository;
import com.mq.service.MqMsgManageService;
import com.mq.service.RabbitMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;

@Slf4j
@Service
public class MqMsgManageServiceImpl implements MqMsgManageService {

    @Inject
    private TbMqMsgMapper tbMqMsgMapper;
    @Inject
    private TbMqMsgRepository tbMqMsgRepository;
    @Inject
    private TbUserRepository tbUserRepository;
    @Inject
    private TbMqMsgPushReleationMapper tbMqMsgPushReleationMapper;
    @Inject
    private RabbitMqService rabbitMqService;
    @Inject
    private ValueHolder valueHolder;

    @Override
    public PageList<TbMqMsg> search(MqMsgSearchRequest param) throws Exception {
        param.setStart(Long.valueOf((param.getPage() - 1) * param.getRows()));
        long c = tbMqMsgMapper.countByOption(param);
        if (c > 0) {
            List<TbMqMsg> list = tbMqMsgMapper.selectByOption(param);
            return new PageList<TbMqMsg>(c, list);
        }
        return new PageList<TbMqMsg>(0, new ArrayList());
    }

    @Override
    public TbMqMsg queryDetail(Long id) {
        Optional<TbMqMsg> tbMqMsgOptional = tbMqMsgRepository.findById(id);
        if (!tbMqMsgOptional.isPresent()) {
            throw new BusinessException(ResponseResultCode.PARAM_ERROR);
        }
        TbMqMsg res = tbMqMsgOptional.get();
        if (res.getActiveBuildMqMsgUserId() != null) {
            Optional<TbUser> buildMqMsgUserOptional = tbUserRepository.findById(res.getActiveBuildMqMsgUserId());
            if (buildMqMsgUserOptional.isPresent()) {
                TbUser buildMqMsgUser = buildMqMsgUserOptional.get();
                res.setRequestPushPlatformStr("管理员：" + buildMqMsgUser.getUname() + "，主动构建消息");
            }
        }
        if (res.getStatus() == 0) {
            res.setStatusStr("未推送");
        } else {
            if (res.getStatus() == 1) {
                res.setStatusStr("推送失败");
            } else if (res.getStatus() == 2) {
                res.setStatusStr(DateUtil.formatDate2Time(res.getDeliverDate()) + " 推送成功");
            } else {
                res.setStatusStr("未知");
            }
            // 处理推送记录
            List<TbMqMsgPushReleation> pushRecordList = tbMqMsgPushReleationMapper.listByMqMsgId(id);
            res.setPushRecordList(pushRecordList);
        }
        if (res.getPushRecordList() == null) {
            res.setPushRecordList(new ArrayList());
        }
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void handBuildMqMsg(TbMqMsg mqMsg) {
        if (mqMsg.getRequestPushIntervalSecond() < 3) {
            throw new BusinessException("间隔时间最小为3秒！");
        }
        Date now = new Date();
        mqMsg.setId(null);
        mqMsg.setCreateDate(now);
        mqMsg.setLastModified(now);
        mqMsg.setRequestPushPlatform(0);
        mqMsg.setActiveBuildMqMsgUserId(valueHolder.getUserIdHolder());
        mqMsg.setStatus(0);
        mqMsg.setTotalPushCount(0);
        tbMqMsgRepository.save(mqMsg);
        rabbitMqService.pushDeadLineMqMsg(RabbitMqConstant.DEFAULT_EXCHANGE,
                RabbitMqConstant.DEFAULT_DEAD_QUEUE,
                mqMsg.getRequestPushMsgContent(),
                mqMsg.getRequestPushIntervalSecond());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String handPushMqMsg(Long id) {
        Optional<TbMqMsg> tbMqMsgOptional = tbMqMsgRepository.findById(id);
        if (!tbMqMsgOptional.isPresent()) {
            return "参数错误";
        }
        TbMqMsg res = tbMqMsgOptional.get();
        if (res.getStatus() == 1 && res.getTotalPushCount() >= 3) {
            // 使用 http请求目标地址
            String resp = HttpClientUtil.postJson(res.getRequestPushDestAddr(), res.getRequestPushMsgContent(), true);
            if (StringUtil.isEmpty(resp)) {
                log.error("接口-/user/mqMsgManage/handPushMqMsg，请求目标地址：{}，请求入参：{}，返回空", res.getRequestPushDestAddr(), res.getRequestPushMsgContent());
                tbMqMsgRepository.updateForFailPush(id);
                return "失败";
            }
            Map<String, Object> map = JsonUtil.jsonToMap(resp);
            if (map == null) {
                log.error("接口-/user/mqMsgManage/handPushMqMsg，请求目标地址：{}，请求入参：{}，返回格式错误", res.getRequestPushDestAddr(), res.getRequestPushMsgContent());
                tbMqMsgRepository.updateForFailPush(id);
                return "失败";
            }
            if (!"200".equals(map.get("code") + "")) {
                log.error("接口-/user/mqMsgManage/handPushMqMsg，请求目标地址：{}，请求入参：{}，返回状态码!=200", res.getRequestPushDestAddr(), res.getRequestPushMsgContent());
                tbMqMsgRepository.updateForFailPush(id);
                return "失败";
            }
            // 交互成功
            tbMqMsgRepository.updateForSuccessPush(id);
            return "成功";
        } else {
            return "不符合手动推送条件";
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void thirdPlatformBuildMqMsg(ThirdPlatformBuildMqMsgRequest param) {
        if (param == null) {
            log.error("接口-/mqMsg/thirdPlatformBuildMqMsg，入参为null");
            throw new BusinessException(ResponseResultCode.PARAM_ERROR);
        }
        if (StringUtil.isEmpty(param.getRequestPushMsgContent()) || param.getRequestPushMsgContent().length() > 495) {
            log.error("接口-/mqMsg/thirdPlatformBuildMqMsg，入参requestPushMsgContent格式错误");
            throw new BusinessException(ResponseResultCode.PARAM_ERROR);
        }
        if (param.getRequestPushPlatform() == null || param.getRequestPushPlatform().intValue() == 0 || StringUtil.isEmpty(EnumUtils.returnValueByKey(param.getRequestPushPlatform(), RequestPushPlatformEnum.class))) {
            log.error("接口-/mqMsg/thirdPlatformBuildMqMsg，入参requestPushPlatform格式错误");
            throw new BusinessException(ResponseResultCode.PARAM_ERROR);
        }
        if (StringUtil.isNotEmpty(param.getRequestPushRemark()) && param.getRequestPushRemark().length() > 250) {
            log.error("接口-/mqMsg/thirdPlatformBuildMqMsg，入参requestPushRemark格式错误");
            throw new BusinessException(ResponseResultCode.PARAM_ERROR);
        }
        if (StringUtil.isEmpty(param.getRequestPushDestAddr()) || param.getRequestPushDestAddr().length() > 250) {
            log.error("接口-/mqMsg/thirdPlatformBuildMqMsg，入参requestPushDestAddr格式错误");
            throw new BusinessException(ResponseResultCode.PARAM_ERROR);
        }
        if (param.getRequestPushIntervalSecond() == null || param.getRequestPushIntervalSecond().intValue() < 3) {
            log.error("接口-/mqMsg/thirdPlatformBuildMqMsg，入参requestPushIntervalSecond格式错误");
            throw new BusinessException(ResponseResultCode.PARAM_ERROR);
        }
        if (param.getRequestQueueNum() != null && !RabbitMqConstant.QUEUE_NUM_LIST.contains(param.getRequestQueueNum())) {
            log.error("接口-/mqMsg/thirdPlatformBuildMqMsg，入参requestQueueNum格式错误");
            throw new BusinessException(ResponseResultCode.PARAM_ERROR);
        }
        TbMqMsg mqMsg = new TbMqMsg();
        BeanUtils.copyProperties(param, mqMsg);
        Date now = new Date();
        mqMsg.setId(null);
        mqMsg.setCreateDate(now);
        mqMsg.setLastModified(now);
        mqMsg.setRequestPushPlatform(param.getRequestPushPlatform());
        mqMsg.setActiveBuildMqMsgUserId(null);
        mqMsg.setStatus(0);
        mqMsg.setTotalPushCount(0);
        tbMqMsgRepository.save(mqMsg);
        rabbitMqService.pushDeadLineMqMsg(RabbitMqConstant.DEFAULT_EXCHANGE,
                RabbitMqConstant.DEFAULT_DEAD_QUEUE,
                mqMsg.getRequestPushMsgContent(),
                mqMsg.getRequestPushIntervalSecond());
    }
}