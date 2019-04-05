package com.mq.service.impl;

import com.mq.common.data.base.PageList;
import com.mq.data.entity.TbMqMsg;
import com.mq.data.to.request.MqMsgSearchRequest;
import com.mq.dbopt.mapper.TbMqMsgMapper;
import com.mq.service.MqMsgManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MqMsgManageServiceImpl implements MqMsgManageService {

    @Inject
    private TbMqMsgMapper tbMqMsgMapper;

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
}