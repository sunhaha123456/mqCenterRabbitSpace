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
    public void defaultRemotePostPushForSuccess(TbMqMsg msg, boolean returnLogFlag) {
        int c = tbMqMsgRepository.updateForSuccessPush(msg.getId());
        if (c !=1 ) {
            log.error("defaultRemotePostPushForSuccess方法，数据库处理失败");
            throw new BusinessException(ResponseResultCode.OPERT_ERROR);
        }
        TbMqMsgPushReleation re = new TbMqMsgPushReleation();
        Date now = new Date();
        re.setCreateDate(now);
        re.setLastModified(now);
        re.setMqMsgId(msg.getId());
        re.setStatus(1);
        re.setPushType(0);
        re.setActivePushMqMsgUserId(null);
        tbMqMsgPushReleationRepository.save(re);
        defaultRemotePostPush(msg.getRequestPushDestAddr(), msg.getRequestPushMsgContent(), true);
    }
}