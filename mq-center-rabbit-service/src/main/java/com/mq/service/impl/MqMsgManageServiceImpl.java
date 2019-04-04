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

@Slf4j
@Service
public class MqMsgManageServiceImpl implements MqMsgManageService {

    @Inject
    private TbMqMsgMapper tbMqMsgMapper;

    @Override
    public PageList<TbMqMsg> search(MqMsgSearchRequest param) throws Exception {
        param.setStart(Long.valueOf(param.getPage() * param.getRows()));

        PageList res = new PageList();
        res.setTotal(0);
        res.setRows(new ArrayList());
        return res;
    }
}