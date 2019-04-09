package com.mq.service.impl;

import com.mq.common.data.response.ResponseResultCode;
import com.mq.common.exception.BusinessException;
import com.mq.common.util.HttpClientUtil;
import com.mq.common.util.JsonUtil;
import com.mq.common.util.StringUtil;
import com.mq.data.entity.TbMqMsg;
import com.mq.data.entity.TbMqMsgPushReleation;
import com.mq.dbopt.repository.TbMqMsgPushReleationRepository;
import com.mq.dbopt.repository.TbMqMsgRepository;
import com.mq.service.ThirdPlatformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ThirdPlatformServiceImpl implements ThirdPlatformService {

    @Inject
    private TbMqMsgRepository tbMqMsgRepository;
    @Inject
    private TbMqMsgPushReleationRepository tbMqMsgPushReleationRepository;

    @Override
    public void defaultRemotePostPush(String destAddr, String content, boolean returnLogFlag) {
        // 使用 http请求目标地址
        String resp = HttpClientUtil.postJson(destAddr, content, returnLogFlag);
        if (StringUtil.isEmpty(resp)) {
            throw new BusinessException(ResponseResultCode.OPERT_ERROR);
        }
        Map<String, Object> map = JsonUtil.jsonToMap(resp);
        if (map == null || !"200".equals(map.get("code") + "")) {
            throw new BusinessException(ResponseResultCode.OPERT_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean defaultRemotePostPushByMsg(Long msgId, boolean returnLogFlag) {
        Optional<TbMqMsg> tbMqMsgOptional = tbMqMsgRepository.findById(msgId);
        if (!tbMqMsgOptional.isPresent()) {
            log.error("defaultRemotePostPushByMsgId方法，msgId:{}，001-查无对应数据记录，不再进行推送，默认做吃掉处理", msgId);
            return true;
        }
        TbMqMsg msg = tbMqMsgOptional.get();
        if (msg == null) {
            log.error("defaultRemotePostPushByMsgId方法，msgId:{}，002-查无对应数据记录，不再进行推送，默认做吃掉处理", msgId);
            return true;
        }
        if (msg.getStatus() != 2 && msg.getTotalPushCount() < 3) {
            int status = 1;
            try {
                defaultRemotePostPush(msg.getRequestPushDestAddr(), msg.getRequestPushMsgContent(), true);
            } catch (Exception e) {
                status = 0;
            }
            int c1 = 0;
            if (status == 1) { // 当推送成功
                c1 = tbMqMsgRepository.updateForSuccessPushWithCheck(msg.getId());
            } else { // 当推送失败
                c1 = tbMqMsgRepository.updateForFailPush(msg.getId());
            }
            if (c1 !=1 ) {
                log.error("defaultRemotePostPushByMsgId方法，004-数据库处理失败");
                throw new BusinessException(ResponseResultCode.OPERT_ERROR);
            }
            TbMqMsgPushReleation re = new TbMqMsgPushReleation();
            Date now = new Date();
            re.setCreateDate(now);
            re.setLastModified(now);
            re.setMqMsgId(msg.getId());
            re.setStatus(status);
            re.setPushType(0);
            re.setActivePushMqMsgUserId(null);
            tbMqMsgPushReleationRepository.save(re);
            return status == 1 ? true : false;
        } else {
            log.error("defaultRemotePostPushByMsgId方法，msgId：{}，003-对应消息，不符合推送条件，不再进行推送，默认做吃掉处理", msgId);
            return true;
        }
    }
}