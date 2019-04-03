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
	private Long mqMsgId;

	// 推送状态
	// 0：推送失败 1：推送成功
	@Column(name = "status", columnDefinition = "INT(1) DEFAULT 0 COMMENT '推送状态'")
	private Integer status;

	// 推送类别
	// 0：系统进行推送 1：管理员主动推送
	@Column(name = "push_type", columnDefinition = "INT(1) DEFAULT 0 COMMENT '推送状态'")
	private Integer pushType;

	// 主动推送消息的管理员id
	// 备注：当推送类别是 1，表示 管理员主动推送时，使用
	@Column(name = "active_push_mq_msg_user_id", columnDefinition = "INT(11) COMMENT '主动推送消息的管理员id'")
	private Long activePushMqMsgUserId;
}