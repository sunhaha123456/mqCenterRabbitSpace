package com.mq.data.entity;

import com.mq.common.data.base.BaseDataIdLong;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 功能：消息表推送记录表
 * @author sunpeng
 * @date 2019
 */
@Data
@Entity
@Table(name = "tb_mq_msg_push_releation")
public class TbMqMsgPushReleation extends BaseDataIdLong {

	@Column(name = "mq_msg_id")
	private String mqMsgId;

	// 推送状态
	// 0：推送失败 1：推送成功
	@Column(name = "status", columnDefinition = "INT(1) DEFAULT 0 COMMENT '推送状态'")
	private Integer status;
}