package com.mq.data.entity;

import com.mq.common.data.base.BaseDataIdStr;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 功能：消息表
 * @author sunpeng
 * @date 2019
 */
@Data
@Entity
@Table(name = "tb_mq_msg")
public class TbMqMsg extends BaseDataIdStr {

	@Column(name = "msg_str", columnDefinition = "varchar(500) COMMENT '消息字符串'")
	private String msgStr;

	// 推送状态
	// 0：未推送 1：推送失败 2：推送成功
	@Column(name = "status", columnDefinition = "INT(1) DEFAULT 0 COMMENT '推送状态'")
	private Integer status;
}