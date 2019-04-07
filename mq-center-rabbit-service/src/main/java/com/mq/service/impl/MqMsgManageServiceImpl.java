package com.mq.service.impl;

import com.mq.common.data.base.PageList;
import com.mq.common.data.response.ResponseResultCode;
import com.mq.common.exception.BusinessException;
import com.mq.common.util.DateUtil;
import com.mq.data.entity.TbMqMsg;
import com.mq.data.entity.TbMqMsgPushReleation;
import com.mq.data.entity.TbUser;
import com.mq.data.to.request.MqMsgSearchRequest;
import com.mq.dbopt.mapper.TbMqMsgMapper;
import com.mq.dbopt.mapper.TbMqMsgPushReleationMapper;
import com.mq.dbopt.repository.TbMqMsgRepository;
import com.mq.dbopt.repository.TbUserRepository;
import com.mq.service.MqMsgManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<TbMqMsgPushReleation> recordList = new ArrayList();
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
        res.setPushRecordList(recordList);
        return res;
    }
}